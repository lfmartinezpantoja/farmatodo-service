package com.famatodo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ServiceException extends Exception {

	private static final long serialVersionUID = -8592504187296748962L;

	private final Integer httpStatus;
	private final String message;

	public ServiceException(Integer httpStatus, String message) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public ServiceException(Integer httpStatus, Enum<?> message) {
		super();
		this.httpStatus = httpStatus;
		this.message = message.name();
	}

	public Integer getHttpStatus() {
		return httpStatus;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public synchronized Throwable fillInStackTrace() {

		return this;
	}

}
