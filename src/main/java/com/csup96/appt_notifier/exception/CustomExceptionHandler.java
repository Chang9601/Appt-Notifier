package com.csup96.appt_notifier.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class CustomExceptionHandler {
	@ExceptionHandler(value = IllegalArgumentException.class)
	public String handleArgumentException(IllegalArgumentException e) {
		return "<h1>" + e.getMessage() + "</h1>";
	}
}