package com.origin.library.domain.exception;

import org.springframework.http.HttpStatus;

import com.origin.library.domain.Error;

public class UsernameOrPasswordError extends Error {
	public UsernameOrPasswordError() {
		super(401, "Username or password is incorrect");
		this.code = HttpStatus.BAD_REQUEST.value();
	}
}
