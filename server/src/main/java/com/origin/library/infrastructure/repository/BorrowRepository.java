package com.origin.library.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.origin.library.domain.Borrow;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
}
