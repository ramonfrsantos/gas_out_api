package br.com.gasoutapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class LoginNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1391752234632451828L;
	
	public LoginNotFoundException() {
        super("Dados de login incorretos.");
    }

    public LoginNotFoundException(String message) {
        super(message);
    }


}
