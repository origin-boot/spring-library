package com.origin.library.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.origin.library.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
