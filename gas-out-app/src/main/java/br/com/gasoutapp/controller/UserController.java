package br.com.gasoutapp.controller;

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

import br.com.gasoutapp.abstracts.BaseResponseDTO;
import br.com.gasoutapp.dto.UserDTO;
import br.com.gasoutapp.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
    private UserService userService;
	
	protected BaseResponseDTO buildResponse(Object object) {
		BaseResponseDTO response = new BaseResponseDTO();
		response.setData(object);
		return response;
	}
	
	@CrossOrigin(origins = "*", maxAge = 7200)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Criar usuário")
	@RequestMapping(path = { "/register" }, method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	public BaseResponseDTO register(@RequestBody UserDTO dto) throws Exception {
		return buildResponse(userService.register(dto));
	}
	
	@CrossOrigin(origins = "*", maxAge = 7200)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Listar todos os usuários")
	@RequestMapping(path = { "/find-all" }, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	public BaseResponseDTO findAll() throws Exception {
		return buildResponse(userService.findAll());
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