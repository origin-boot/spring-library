package com.origin.library.domain.exception;

import org.springframework.http.HttpStatus;

import com.origin.library.domain.Error;

public class InternalServerError extends Error {
	public InternalServerError() {
		super(500, "Internal Server Error");
		this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
	}
}
