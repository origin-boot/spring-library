package com.origin.library.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.origin.library.domain.User;
import com.origin.library.infrastructure.querydsl.ShortcutPredicateExecutor;

public interface UserRepository
    extends JpaRepository<User, Long>, ShortcutPredicateExecutor<User> {

  Optional<User> findByUsername(String username);
}