package br.com.gasoutapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gasoutapp.domain.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}