package br.com.gasoutapp.service;

import java.security.SecureRandom;
import java.text.Normalizer;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.gasoutapp.domain.User;
import br.com.gasoutapp.domain.enums.UserTypeEnum;
import br.com.gasoutapp.dto.UserDTO;
import br.com.gasoutapp.exception.LoginNotFoundException;
import br.com.gasoutapp.exception.UserAlreadyRegisteredException;
import br.com.gasoutapp.exception.UserNotFoundException;
import br.com.gasoutapp.exception.WrongPasswordException;
import br.com.gasoutapp.repository.UserRepository;
import br.com.gasoutapp.security.CriptexCustom;
import br.com.gasoutapp.security.LoginResultDTO;
import br.com.gasoutapp.security.TokenService;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String companyEmail;

    private String adminEmail = "ramonfrsantos@gmail.com";

    public User parseDTOToEntity(UserDTO userDTO) {
        User newUser;
        User user = userRepository.findByEmail(userDTO.getEmail());

        if (user == null) {
            user = new User();
        } else {
            throw new UserAlreadyRegisteredException();
        }

        newUser = user;

        if (userDTO.getName() != null && !userDTO.getName().equals("")) {
            newUser.setName(normalizeString(userDTO.getName()));
        }
        if (userDTO.getEmail() != null && !userDTO.getEmail().equals("")) {
            newUser.setEmail(userDTO.getEmail());
        }

        newUser.setLogin(userDTO.getEmail());
        newUser.setLastUpdate(new Date());

        String password = CriptexCustom.encrypt(userDTO.getPassword());

        newUser.setPassword(password);

        if (userDTO.getEmail().equals(adminEmail)) {
            newUser.getRoles().add(UserTypeEnum.ADMIN);
        } else {
            newUser.getRoles().add(UserTypeEnum.CLIENTE);
        }

        return newUser;
    }

    @ExceptionHandler({Exception.class})
    public LoginResultDTO login(String login, String password, String token) throws Exception {
        if (password.length() < 6) {
            throw new WrongPasswordException();
        }
        password = CriptexCustom.encrypt(password);
        User user = userRepository.findByLoginAndPassword(login, password);
        User userLogin = userRepository.findByLogin(login);
        if (user == null) {
            if (userLogin == null) {
                throw new LoginNotFoundException();
            }
            if (!userLogin.getPassword().equals(password)) {
                throw new WrongPasswordException();
            }
        }

        LoginResultDTO dto = this.tokenService.createTokenForUser(user);
        dto.setUserId(user.getId());
        if (user.getName() != null && !user.getName().equals("")) {

            dto.setUserName(normalizeString(user.getName()));
        }
        if (token != null && !user.getDevices().contains(token)) {
            user.getDevices().add(token);
        }
        userRepository.save(user);

        return dto;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User register(UserDTO userDTO) {
        User user = parseDTOToEntity(userDTO);
        return userRepository.save(user);
    }

    public void delete(String login) {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            user.setDeleted(true);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException();
        }
    }

    public String getVerificationCode(String login) {
        User user = userRepository.findByEmail(login);
        if (user == null) {
            throw new UserNotFoundException();
        }

        return user.getVerificationCode();
    }

    public boolean checkIfCodesAreEqual(String login, String newCode) {
        User user = userRepository.findByEmail(login);
        if (user == null) {
            throw new UserNotFoundException();
        }

        if (user.getVerificationCode().equals(newCode)) {
            return true;
        } else {
            return false;
        }
    }

    public String normalizeString(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public String sendVerificationMail(String login) {
        User newUser;
        User user = userRepository.findByEmail(login);
        if (user == null) {
            throw new UserNotFoundException();
        }
        newUser = user;

        System.out.println("Preparando para enviar a mensagem...");

        String verificationCode = this.createRandomCode(6, "0123456789");

        newUser.setVerificationCode(verificationCode);
        newUser = userRepository.save(newUser);

        String fullName = newUser.getName();
        String firstName = fullName.split(" ", 0)[0];

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(companyEmail);
        message.setTo(newUser.getEmail());
        message.setText("Olá, " + firstName + "! Seu código de verificação para a alteração da senha é:\n\n" + newUser.getVerificationCode() + "\n\nAgora é só entrar no aplicativo GasOut e escolher uma nova senha.");
        message.setSubject("Alteração de senha no aplicativo GasOut");

        mailSender.send(message);

        System.out.println("A Mensagem foi enviada.");

        return newUser.getVerificationCode();
    }

    public String createRandomCode(int tamanhoCodigo, String caracteresUsados) {
        List<Character> grupoCaracteres = caracteresUsados.chars().mapToObj(i -> (char) i).collect(Collectors.toList());
        Collections.shuffle(grupoCaracteres, new SecureRandom());
        return grupoCaracteres.stream().map(Object::toString).limit(tamanhoCodigo).collect(Collectors.joining());
    }

    public User refreshPassword(String password, String login) {
        User newUser;
        User user = userRepository.findByEmail(login);
        if (user == null) {
            throw new UserNotFoundException();
        }
        newUser = user;

        newUser.setPassword(CriptexCustom.encrypt(password));
        return userRepository.save(newUser);
    }
}