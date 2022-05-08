package br.com.gasoutapp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class NotificationDTO {

	private Long id;
    private String message;
    private String title;
	private Date date;	
	private String email;
	
	public NotificationDTO(Long id) {
		this.id = id;
	}
		
	public NotificationDTO() {
	}
}