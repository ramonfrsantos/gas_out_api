package br.com.gasoutapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.OK)
public class RoomAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1391752234632451828L;
	
	public RoomAlreadyExistsException() {
        super("Esse cômodo já foi cadastrado.");
    }

    public RoomAlreadyExistsException(String message) {
        super(message);
    }
}