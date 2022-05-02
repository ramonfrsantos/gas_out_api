package br.com.gasoutapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gasoutapp.domain.Notification;
import br.com.gasoutapp.dto.NotificationDTO;
import br.com.gasoutapp.exception.NotificationNotFoundException;
import br.com.gasoutapp.repository.NotificationRepository;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;
	
	public List<Notification> getAllNotifications() {
		return notificationRepository.findAll();
	}
	
	public List<Notification> getAllRecentNotifications() {
		List<Notification> notifications = notificationRepository.findAll();
		reverseList(notifications);
				
		return notifications;
	}

	public Notification createNotification(NotificationDTO dto) {
		
		List<Notification> notifications = getAllNotifications();
		if(notifications.size() >= 10) {
			deleteAllNotifications(notifications);
		}
		
		Notification newNotification = new Notification();
		
		newNotification.setTitle(dto.getTitle());
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
	
	public void deleteAllNotifications(List<Notification> notifications) {
		notificationRepository.deleteAll(notifications);
	}
	
	public static<T> void reverseList(List<T> list) {
        // base case: the list is empty, or only one element is left
        if (list == null || list.size() <= 1) {
            return;
        }
        // remove the first element
        T value = list.remove(0);
        // recur for remaining items
        reverseList(list);
        // insert the top element back after recurse for remaining items
        list.add(value);
    }
}