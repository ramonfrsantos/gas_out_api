package com.gasoutapp.controller;

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

import com.gasoutapp.model.domain.Notification;
import com.gasoutapp.model.dto.NotificationDTO;
import com.gasoutapp.model.service.NotificationService;

@RestController
@RequestMapping("notification")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;
	
	@CrossOrigin(origins = "*", maxAge = 7200)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/find-all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public List<Notification> getAllNotifications(){
		return notificationService.getAllNotifications();
	}
	
	@CrossOrigin(origins = "*", maxAge = 7200)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Notification createNotification(@RequestBody NotificationDTO dto){
		return notificationService.createNotification(dto);
	}	
	
	@CrossOrigin(origins = "*", maxAge = 7200)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	public void deleteNotification(@PathVariable Long id){
		notificationService.deleteNotification(id);
	}	
}
