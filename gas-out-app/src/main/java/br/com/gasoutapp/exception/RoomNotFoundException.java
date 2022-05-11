package br.com.gasoutapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RoomNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1391752234632451828L;
	
	public RoomNotFoundException() {
        super("Cômodo com o id informado não está cadastrado.");
    }

    public RoomNotFoundException(String message) {
        super(message);
    }
}