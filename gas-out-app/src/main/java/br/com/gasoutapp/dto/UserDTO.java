package br.com.gasoutapp.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO {

	private Long id;
	private String name;
	private String email;	
	private String password;

	public UserDTO() {
	}
}