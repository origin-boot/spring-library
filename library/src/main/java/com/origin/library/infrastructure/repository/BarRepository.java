package com.origin.library.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.origin.library.domain.Bar;
import com.origin.library.infrastructure.querydsl.ShortcutPredicateExecutor;

public interface BarRepository
    extends JpaRepository<Bar, Long>, ShortcutPredicateExecutor<Bar> {

}