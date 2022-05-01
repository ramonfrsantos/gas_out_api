package com.gasoutapp.model.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gasoutapp.model.domain.Notification;
import com.gasoutapp.model.dto.NotificationDTO;
import com.gasoutapp.model.exception.NotificationNotFoundException;
import com.gasoutapp.model.repository.NotificationRepository;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	public List<Notification> getAllNotifications() {
		return notificationRepository.findAll();
	}

	public Notification createNotification(NotificationDTO dto) {
		Notification newNotification = new Notification();
		
		newNotification.setMessage(dto.getMessage());
		newNotification.setDate(new Date());
		
		return notificationRepository.save(newNotification);
	}

	public void deleteNotification(Long id) {
		Notification notification = notificationRepository.getById(id);
		if(notification != null) {
			notificationRepository.delete(notification);
		} else {
			throw new NotificationNotFoundException();
		}
		
	}


}
