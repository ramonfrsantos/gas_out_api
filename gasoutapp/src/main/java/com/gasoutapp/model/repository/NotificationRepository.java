package com.gasoutapp.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gasoutapp.model.domain.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}