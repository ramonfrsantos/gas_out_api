package br.com.gasoutapp.dto;

import lombok.Data;

@Data
public class RoomDTO {
	private Long id;
	private String name;
    private boolean alarmOn;
	private boolean sprinklersOn;	
	private Integer numberOfSensors;	

	public RoomDTO(Long id) {
		this.id = id;
	}


	public RoomDTO() {
	}
}