package br.com.gasoutapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotificationNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1391752234632451828L;
	
	public NotificationNotFoundException() {
        super("Notificação não encontrada.");
    }

    public NotificationNotFoundException(String message) {
        super(message);
    }
}