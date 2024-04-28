package com.origin.library.restful;

import java.util.List;

import com.origin.library.domain.Page;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
class LoginCommand {
    @Length(min = 4, max = 20, message = "username length must be between 4 and 20")
    private String username;

    @Length(min = 4, max = 20, message = "password length must be between 4 and 20")
    private String password;
}

@Data
class SearchBooksQuery {
	@Length(max = 30, message = "name length must be less than 30")
    private String name;

    @NotNull(message = "is whether to filter books that I have borrowed or returned")
    private Boolean mine;

    @Min(value = 1, message = "pageNum must be greater than 0")
    private int pageNum;

    @Range(min = 5, max = 100, message = "pageSize must be between 1 and 100")
    private int pageSize;
}

class SearchBooksResponse extends Page<BookResource> {
	SearchBooksResponse(List<BookResource> records, int total) {
		super(records, total);
	}
}
