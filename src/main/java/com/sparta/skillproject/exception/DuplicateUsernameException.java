package com.sparta.skillproject.exception;

public class DuplicateUsernameException extends RuntimeException {
	public DuplicateUsernameException(String message) {
		super(message);
	}
}
