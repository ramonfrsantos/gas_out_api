package br.com.gasoutapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED, reason = "UserDisabledException")
public class UserDisabledException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2283367251301710937L;

}
