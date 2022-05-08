package br.com.gasoutapp.dto;

import lombok.Data;

@Data
public class UserDTO {

	private Long id;
	private String name;
	private String email;	
	private String password;

	public UserDTO(Long id) {
		this.id = id;
	}
	
	public UserDTO() {
	}
}