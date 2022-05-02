package br.com.gasoutapp.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "t_notification")
public class Notification {
	@Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private Long id;
	
	@Column(name = "title")
    private String title;
	
	@Column(name = "message")
    private String message;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "notification_date")
	private Date date;

}