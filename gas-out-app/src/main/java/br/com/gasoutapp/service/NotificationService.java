package br.com.gasoutapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gasoutapp.domain.Notification;
import br.com.gasoutapp.domain.User;
import br.com.gasoutapp.dto.NotificationDTO;
import br.com.gasoutapp.exception.NotificationNotFoundException;
import br.com.gasoutapp.exception.UserNotFoundException;
import br.com.gasoutapp.repository.NotificationRepository;
import br.com.gasoutapp.repository.UserRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }


    public List<Notification> getAllRecentNotifications(String login) {
        User user = userRepository.findByLogin(login);
        List<Notification> notifications = notificationRepository.findAllByUser(user);
        reverseList(notifications);

        return notifications;
    }

    public Notification createNotification(NotificationDTO dto) {
        List<Notification> newUserNotifications = new ArrayList<>();
        User newUser;
        User user = userRepository.findByLogin(dto.getEmail());
        if (user == null) {
            throw new UserNotFoundException();
        }
        newUser = user;

        List<Notification> notifications = notificationRepository.findAllByUser(user);
        if (notifications.size() >= 10) {
            setAllUserNotificationsNull(notifications, user);
        } else {
            newUserNotifications = notifications;
        }

        Notification newNotification = new Notification();

        newNotification.setUser(user);
        newNotification.setTitle(dto.getTitle());
        newNotification.setMessage(dto.getMessage());
        newNotification.setDate(new Date());

        newNotification = notificationRepository.save(newNotification);
        newUserNotifications.add(newNotification);

        newUser.setNotifications(newUserNotifications);
        userRepository.save(newUser);

        List<Notification> notificationsUserNull = notificationRepository.findAllByUser(null);
        if (notificationsUserNull.size() > 0) {
            notificationRepository.deleteAll(notificationsUserNull);
        }

        return newNotification;
    }

    public void deleteNotification(Long id, String login) {
        Notification checkedNotification = notificationRepository.getById(id);
        if (checkedNotification == null) {
            throw new NotificationNotFoundException();
        }

        User user = userRepository.findByLogin(login);
        if (user != null) {
            User newUser;
            newUser = user;
            List<Notification> notificationsList = notificationRepository.findAllByUser(user);
            for (Notification notification : notificationsList) {
                if (notification.getId() == id) {
                    notification.setUser(null);
                    notificationRepository.save(notification);

                    newUser.setNotifications(notificationRepository.findAllByUser(user));
                    userRepository.save(newUser);
                    notificationRepository.delete(notification);
                }
            }
        }
    }

    public void setAllUserNotificationsNull(List<Notification> notifications, User user) {
        if (user != null) {
            User newUser;
            newUser = user;
            List<Notification> newNotificationsList = new ArrayList<>();
            newUser.setNotifications(newNotificationsList);
            userRepository.save(newUser);
        }
        for (Notification notification : notifications) {
            notification.setUser(null);
            notificationRepository.save(notification);
        }
    }

    public static <T> void reverseList(List<T> list) {
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