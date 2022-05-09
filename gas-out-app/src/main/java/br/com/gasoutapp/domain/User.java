package br.com.gasoutapp.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import br.com.gasoutapp.domain.enums.UserTypeEnum;
import lombok.Data;

@DynamicUpdate
@Data
@Entity
@Table(name = "t_user")
@Where(clause = "deleted = false")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
	
	@Column(name = "name")
    private String name;
	
	@Column(name = "login")
    private String login;
	
	@Column(name = "email")
    private String email;
	
	@Column(name = "password")
    private String password;
	
	@Column(name = "deleted")
    private boolean deleted;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@Column(name = "fk_notification")
	private List<Notification> notifications = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@Column(name = "fk_room")
	private List<Room> rooms = new ArrayList<>();
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update")
    private Date lastUpdate;
	
	@ElementCollection
    @CollectionTable(name = "t_user_role", joinColumns = @JoinColumn(name = "fk_user"))
    @Column(name = "role")
    private List<UserTypeEnum> roles = new ArrayList<>();
	
	@ElementCollection
    @CollectionTable(name = "t_user_device", joinColumns = @JoinColumn(name = "fk_user"))
    @Column(name = "device")
    private List<String> devices = new ArrayList<>();
}
