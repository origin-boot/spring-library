package com.origin.library.restful;

import java.util.List;

import com.origin.library.domain.Page;

public class ListBooksResponse extends Page<BookResource> {
	ListBooksResponse(List<BookResource> records, int total) {
		super(records, total);
	}
}
