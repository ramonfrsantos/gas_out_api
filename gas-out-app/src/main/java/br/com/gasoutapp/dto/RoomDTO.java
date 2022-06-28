package br.com.gasoutapp.dto;

import br.com.gasoutapp.domain.User;
import lombok.Data;

@Data
public class RoomDTO {
	private Long id;
	private String name;
	private String userEmail;
    private boolean alarmOn;
	private boolean notificationOn;
	private boolean sprinklersOn;	
	private Integer sensorValue;
	private User user;

	public RoomDTO(Long id) {
		this.id = id;
	}


	public RoomDTO() {}
}