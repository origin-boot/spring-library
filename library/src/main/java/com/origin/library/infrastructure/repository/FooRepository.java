package com.origin.library.infrastructure.repository;


import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.origin.library.domain.Page;
import com.origin.library.domain.QFoo;
import com.origin.library.domain.Foo;
import com.origin.library.infrastructure.querydsl.ShortcutExecute;
import com.origin.library.infrastructure.querydsl.ShortcutPredicateExecutor;
import com.querydsl.core.types.Predicate;

public interface FooRepository
    extends JpaRepository<Foo, Long>, ShortcutPredicateExecutor<Foo>, AdvancedFooRepository {

  // FIXME: remove the example
  default Page<Foo> searchFoos(String keyword, int pageNumber, int pageSize) {
    Predicate p = predicate()
        .build();
    Sort sort = asc("id");
    return findAll(p, sort, pageNumber, pageSize);
  }
}

interface AdvancedFooRepository {
  Page<Foo> searchFoos(String keyword, int pageSize);

  boolean editFooCreateTime(long fooId);

  long removeFooBeforeTime(long createTime);

  void searchBeforeEdit(boolean throwException);
}

@Service
class AdvancedFooRepositoryImpl extends ShortcutExecute implements AdvancedFooRepository {
  // FIXME: remove the example
  @Override
  public Page<Foo> searchFoos(String keyword, int pageSize) {
    QFoo a = QFoo.foo;
    // and id>0 and (id>0 and id<1000) and (id>-1 or id<1001)
    // or id<0 or (id<-1 or id<-2) or (id<-3 and id<-4)
    Predicate p = predicate()
        .and(a.id.gt(0))
        .and(a.id.gt(0), a.id.lt(1000))
        .andAnyOf(true, a.id.gt(-1), a.id.lt(1001))
        .or(a.id.lt(0))
        .or(a.id.lt(-1), a.id.lt(-2))
        .orAllOf(true, a.id.lt(-3), a.id.lt(-4))
        .build();
    Page<Foo> r = findAll(
        q -> q.select(a)
            .from(a)
            .where(p),
        pageSize);
    return r;
  }

  // FIXME: remove the example
  @Override
  @Transactional
  public boolean editFooCreateTime(long fooId) {
    QFoo a = QFoo.foo;
    Predicate p = predicate().and(a.id.eq(fooId)).build();
    long rowsAffected = update(a)
        .set(a.createTime, a.createTime.add(1))
        .where(p)
        .execute();
    return rowsAffected > 0;
  }

  // FIXME: remove the example
  @Override
  @Transactional
  public long removeFooBeforeTime(long createTime) {
    QFoo a = QFoo.foo;
    Predicate p = predicate().and(a.createTime.lt(createTime)).build();
    long rowsAffected = delete(a)
        .where(p)
        .execute();
    return rowsAffected;
  }

  // FIXME: remove the example
  @Override
  @Transactional
  public void searchBeforeEdit(boolean throwException) {
    this.searchFoos("", 10);
    this.editFooCreateTime(1);
    this.removeFooBeforeTime(1);
    if (throwException) {
      throw new RuntimeException("throw an exception for rollback");
    }
  }
}