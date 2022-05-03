package br.com.gasoutapp.dto;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NotificationDTO {

	private Long id;
    private String message;
    private String title;
	private Date date;	
    private String monitoringTime;
	
	public NotificationDTO(Long id) {
		this.id = id;
	}
		
	public NotificationDTO() {
	}
}