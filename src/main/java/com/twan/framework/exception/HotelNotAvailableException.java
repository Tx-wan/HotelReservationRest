package com.twan.framework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class HotelNotAvailableException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public HotelNotAvailableException(String message) {
		super(message);
	}
}
