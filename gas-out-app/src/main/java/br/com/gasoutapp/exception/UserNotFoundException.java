package br.com.gasoutapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.OK)
public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1391752234632451828L;
	
	public UserNotFoundException() {
        super("Usuário não encontrado.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}