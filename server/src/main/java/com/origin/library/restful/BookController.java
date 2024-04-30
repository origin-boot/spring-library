package com.origin.library.restful;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.origin.library.domain.User;
import com.origin.library.domain.error.BookNotFoundError;
import com.origin.library.domain.error.RequestForbiddenError;
import com.origin.library.domain.error.UserNotFoundError;
import com.origin.library.domain.success.Empty;
import com.origin.library.domain.success.Ok;
import com.origin.library.infrastructure.controller.BaseController;
import com.origin.library.infrastructure.controller.RequestUser;

import jakarta.validation.Valid;

@RestController
public class BookController extends BaseController {

	@Autowired
	BookService bookService;

	@GetMapping("/api/books")
	public Ok<SearchBooksResponse> searchBooks(@RequestUser User user, @Valid SearchBooksQuery query)
			throws UserNotFoundError {

		if (query.getMine()) {
			SearchBooksResponse response = bookService.searchMyBooks(user, query);
			return Ok.of(response);
		}

		SearchBooksResponse response = bookService.searchBooks(user, query);
		return Ok.of(response);
	}

	@PostMapping("/api/books/{id}/borrow")
	public Ok<Empty> borrowBook(@RequestUser User user, @PathVariable("id") final long id)
			throws BookNotFoundError,
			RequestForbiddenError,
			UserNotFoundError {

		bookService.borrowBook(user, id);

		return Ok.empty();
	}

	@PostMapping("/api/books/{id}/return")
	public Ok<Empty> returnBook(@RequestUser User user, @PathVariable("id") final long id)
			throws BookNotFoundError,
			RequestForbiddenError,
			UserNotFoundError {

		bookService.returnBook(user, id);

		return Ok.empty();
	}
}
