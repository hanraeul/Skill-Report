package com.sparta.skillproject.exception;

public class PasswordMismatchException extends RuntimeException {
	public PasswordMismatchException(String message) {
		super(message);
	}
}
