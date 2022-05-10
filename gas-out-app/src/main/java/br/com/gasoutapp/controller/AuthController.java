package br.com.gasoutapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.gasoutapp.dto.LoginDTO;
import br.com.gasoutapp.security.LoginResultDTO;
import br.com.gasoutapp.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("auth")
public class AuthController {
	@Autowired
    private UserService userService;

    @CrossOrigin(origins = "*", maxAge = 7200)
    @ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Login do usuário")
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public LoginResultDTO login(@RequestBody LoginDTO dto) throws Exception {
        return userService.login(dto.getLogin(), dto.getPassword(), dto.getToken());
    }
}