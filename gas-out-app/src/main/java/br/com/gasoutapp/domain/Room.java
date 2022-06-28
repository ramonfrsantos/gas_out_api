package br.com.gasoutapp.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import org.hibernate.annotations.Where;

@DynamicUpdate
@Data
@Entity
@Table(name = "t_room")
@Where(clause = "deleted = false")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "notification_on")
    private boolean notificationOn;

    @Column(name = "alarm_on")
    private boolean alarmOn;

    @Column(name = "sprinklers_on")
    private boolean sprinklersOn;

    @Column(name = "sensor_value")
    private Integer sensorValue;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "deleted")
    private boolean deleted;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user")
    private User user;
}