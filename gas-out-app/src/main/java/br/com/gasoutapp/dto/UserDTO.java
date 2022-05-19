package br.com.gasoutapp.dto;

import lombok.Data;

@Data
public class UserDTO {
	private Long id;
	private String name;
	private String email;	
	private String password;
	private String verificationCode;

	public UserDTO(Long id) {
		this.id = id;
	}
	
	public UserDTO() {}
}