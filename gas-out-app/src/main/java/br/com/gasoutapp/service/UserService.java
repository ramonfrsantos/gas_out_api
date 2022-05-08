package br.com.gasoutapp.service;

import java.text.Normalizer;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	private String adminEmail = "ramonfrsantos@gmail.com";
	
	public User parseDTOToEntity(UserDTO userDTO) {
		User newUser = new User();
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

		newUser.setPassword(CriptexCustom.encrypt(password));
		
		if(userDTO.getEmail().equals(adminEmail)) {
			newUser.getRoles().add(UserTypeEnum.ADMIN);			
		} else {
			newUser.getRoles().add(UserTypeEnum.CLIENTE);						
		}

		return newUser;
	}

	@ExceptionHandler({ Exception.class })
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
		if(user != null) {
			userRepository.delete(user);			
		} else {
			throw new UserNotFoundException();
		}
	}
	
	public String normalizeString(String string) {
		String stringNormalizada = Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		
		return stringNormalizada;
	}
}