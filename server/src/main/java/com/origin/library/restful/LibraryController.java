package com.origin.library.restful;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import com.origin.library.domain.Page;

@RestController
public class LibraryController {

	@GetMapping("/api/books")
	public ResponseEntity<Page<BookResource>> listBooks(
			// FIXME: validate name, username, pageNum, pageSize
			// Use "@RequestBody @Valid ListBooksQuery listBooksQuery" instead of using RequestParam
			// Use ListBooksResponse instead of Page<BookResource>
			@RequestParam(value = "name", defaultValue = "") final String name,
			@RequestParam(value = "username", defaultValue = "") final String username,
			@RequestParam(value = "pageNum", defaultValue = "1") final int pageNum,
			@RequestParam(value = "pageSize", defaultValue = "5") final int pageSize) {
		

		// FIXME: Query from database instead of using dummy data
		// use BookRepository & UserRepository to query from database

		List<BookResource> totalRecords = List.of(
				BookResource.of(),
				BookResource.of(),
				BookResource.of(),
				BookResource.of(),
				BookResource.of(),
				BookResource.of(),
				BookResource.of(),
				BookResource.of());

		Pageable pageable = PageRequest.of(pageNum - 1, pageSize);

		List<BookResource> pageRecords = totalRecords.subList(
				(int) pageable.getOffset(),
				(int) Math.min(pageable.getOffset() + pageable.getPageSize(), totalRecords.size()));

		Page<BookResource> page = new Page<BookResource>(pageRecords, totalRecords.size());

		return ResponseEntity.ok(page);
	}

	@PostMapping("/api/books/{id}/borrow")
	public ResponseEntity<Void> borrowBook(@PathVariable("id") final int id) {

		// FIXME: Implement by BorrowService.borrowBook

		return ResponseEntity.ok().build();
	}

	@PostMapping("/api/books/{id}/return")
	public ResponseEntity<Void> returnBook(@PathVariable("id") final int id) {

		// FIXME: Implement by BorrowService.returnBook

		return ResponseEntity.ok().build();
	}
}
