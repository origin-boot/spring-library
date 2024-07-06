package com.origin.library.infrastructure.querydsl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

import com.origin.library.domain.Page;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAInsertClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;

public class ShortcutExecute implements ShortcutExecutor {
  @PersistenceContext
  protected EntityManager em;

  public JPAInsertClause insert(EntityPath<?> entity) {
    return new JPAInsertClause(em, entity);
  }

  public JPADeleteClause delete(EntityPath<?> entity) {
    return new JPADeleteClause(em, entity);
  }

  public JPAUpdateClause update(EntityPath<?> entity) {
    return new JPAUpdateClause(em, entity);
  }

  public <T> List<T> findAll(ShortcutQueryFunction<T> queryFunction) {
    JPAQuery<T> query = new JPAQuery<>(em);
    queryFunction.apply(query);
    
    List<T> list = query.fetch();
    return list;
  }


  public <T> Page<T> findAll(ShortcutQueryFunction<T> queryFunction, int pageNumber, int pageSize) {
    JPAQuery<T> query = new JPAQuery<>(em);
    queryFunction.apply(query);

    @SuppressWarnings("deprecation")
    long total = query.fetchCount();

    if (total == 0) {
      return Page.empty();
    }

    List<T> list = query
        .offset(pageOffset(pageNumber, pageSize))
        .limit(pageSize(pageSize))
        .fetch();

    return new Page<>(list, total);
  }

  public <T> Page<T> findAll(ShortcutQueryFunction<T> queryFunction, int pageSize) {
    return findAll(queryFunction, 0, pageSize);
  }
}
