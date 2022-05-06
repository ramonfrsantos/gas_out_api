package br.com.gasoutapp.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "t_notification")
public class Notification {
	@Id
    @GeneratedValue(generator = "sq_notification")
    @SequenceGenerator(name = "sq_notification", sequenceName = "sq_notification", allocationSize = 1)
    @Column(name = "id")
    private Long id;
	
	@Column(name = "title")
    private String title;
	
	@Column(name = "message")
    private String message;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "notification_date")
	private Date date;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "fk_user")
	private User user;

}