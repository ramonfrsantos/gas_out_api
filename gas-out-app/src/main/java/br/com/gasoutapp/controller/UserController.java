package br.com.gasoutapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.gasoutapp.domain.User;
import br.com.gasoutapp.dto.UserDTO;
import br.com.gasoutapp.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
    private UserService userService;
	
	@CrossOrigin(origins = "*", maxAge = 7200)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Envia email de verificação de senha")
	@RequestMapping(path = { "/send-verification-mail/{login}" }, method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	public String sendVerificationMail(@PathVariable String login) throws Exception {
		return userService.sendVerificationMail(login);
	}
	
	@CrossOrigin(origins = "*", maxAge = 7200)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Buscar código de verificação de senha")
	@RequestMapping(path = { "/verification-code/{login}" }, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	public String getVerificationCode(@PathVariable String login) throws Exception {
		return userService.getVerificationCode(login);
	}
	
	@CrossOrigin(origins = "*", maxAge = 7200)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Verificar se códigos são iguais")
	@RequestMapping(path = { "/check-codes-equal/{newCode}/{login}" }, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	public boolean checkIfCodesAreEqual(@PathVariable String login, @PathVariable String newCode) throws Exception {
		return userService.checkIfCodesAreEqual(login, newCode);
	}
	
	@CrossOrigin(origins = "*", maxAge = 7200)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Criar usuário")
	@RequestMapping(path = { "/register" }, method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	public User register(@RequestBody UserDTO dto) throws Exception {
		return userService.register(dto);
	}
	
	@CrossOrigin(origins = "*", maxAge = 7200)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Cria nova senha")
	@RequestMapping(path = { "/refresh/{login}/{password}" }, method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	public User sendVerificationMail(@PathVariable String password, @PathVariable String login) throws Exception {
		return userService.refreshPassword(password, login);
	}
	
	@CrossOrigin(origins = "*", maxAge = 7200)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Listar todos os usuários")
	@RequestMapping(path = { "/find-all" }, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	public List<User> findAll() throws Exception {
		return userService.findAll();
	}
	
	@CrossOrigin(origins = "*", maxAge = 7200)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Exclui usuário")
	@RequestMapping(path = { "/delete/{login}" }, method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	public void delete(@PathVariable String login) throws Exception {
		userService.delete(login);
	}
}