package com.origin.library.domain.error;

import org.springframework.http.HttpStatus;

public class BookNotFoundError extends Error {
	public BookNotFoundError() {
		super(Code.BOOK_NOT_FOUND.value(), "Book not found", "", HttpStatus.NOT_FOUND.value());
	}

	@Override
	public BookNotFoundError setDetails(String details) {
		super.setDetails(details);
		return this;
	}
}
