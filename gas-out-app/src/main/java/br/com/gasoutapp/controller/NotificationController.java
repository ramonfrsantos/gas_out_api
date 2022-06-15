package br.com.gasoutapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.gasoutapp.domain.Notification;
import br.com.gasoutapp.dto.NotificationDTO;
import br.com.gasoutapp.service.NotificationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("notification")
public class NotificationController {
	@Autowired
	private NotificationService notificationService;
	
	@CrossOrigin(origins = "*", maxAge = 7200)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Listar notificações")
	@RequestMapping(value = "/find-all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	public List<Notification> getAllNotifications(){
		return notificationService.getAllNotifications();
	}
	
	@CrossOrigin(origins = "*", maxAge = 7200)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Listar notificações recentes")
	@RequestMapping(value = "/find-all-recent/{login}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	public List<Notification> getAllRecentNotifications(@PathVariable String login){
		return notificationService.getAllRecentNotifications(login);
	}
	
	@CrossOrigin(origins = "*", maxAge = 7200)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Criar notificação")
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	public Notification createNotification(@RequestBody NotificationDTO dto){
		return notificationService.createNotification(dto);
	}	
	
	@CrossOrigin(origins = "*", maxAge = 7200)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Excluir notificação por id")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	public void deleteNotification(@PathVariable Long id){
		notificationService.deleteNotification(id);
	}	
}