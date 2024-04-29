package com.origin.library.restful;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.origin.library.domain.User;
import com.origin.library.domain.error.BookNotFoundError;
import com.origin.library.domain.error.RequestForbiddenError;
import com.origin.library.domain.success.Empty;
import com.origin.library.domain.success.Ok;

import jakarta.validation.Valid;

@RestController
public class BookController {

	@Autowired
	BookService bookService;

	@GetMapping("/api/books")
	public Ok<SearchBooksResponse> searchBooks(
			@Valid SearchBooksQuery query) {

		User user = new User();
		user.setId(1);
		user.setUsername("user1");

		if (query.getMine()) {
			SearchBooksResponse response = bookService.searchMyBooks(user, query);
			return Ok.of(response);
		}

		SearchBooksResponse response = bookService.searchBooks(user, query);
		return Ok.of(response);
	}

	@PostMapping("/api/books/{id}/borrow")
	public Ok<Empty> borrowBook(@PathVariable("id") final long id) throws BookNotFoundError, RequestForbiddenError {

		User user = new User();
		user.setId(1);
		user.setUsername("user1");

		bookService.borrowBook(user, id);

		return Ok.empty();
	}

	@PostMapping("/api/books/{id}/return")
	public Ok<Empty> returnBook(@PathVariable("id") final long id) throws BookNotFoundError, RequestForbiddenError {

		User user = new User();
		user.setId(1);
		user.setUsername("user1");

		bookService.returnBook(user, id);

		return Ok.empty();
	}
}
