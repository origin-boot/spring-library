package com.origin.library.infrastructure.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.origin.library.domain.Page;
import com.origin.library.domain.QFoo;
import com.origin.library.domain.Foo;
import com.origin.library.domain.dto.A2Count;
import com.origin.library.infrastructure.querydsl.ShortcutExecute;
import com.origin.library.infrastructure.querydsl.ShortcutPredicateExecutor;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;

import java.util.ArrayList;
import java.util.List;

public interface FooRepository
    extends JpaRepository<Foo, Long>, ShortcutPredicateExecutor<Foo>, AdvancedFooRepository {

  default Page<Foo> searchExample1(String keyword, int pageNumber, int pageSize) {
    QFoo a = QFoo.foo;
    Predicate p = predicate()
        .and(isNotBlank(keyword), a.a3.like(quoteLike(keyword)))
        .build();
    Sort sort = orderBy(a.id.desc());
    return findAll(p, sort, pageNumber, pageSize);
  }

  default Iterable<Foo> searchExample2(String keyword) {
    QFoo a = QFoo.foo;
    Predicate p = predicate()
        .and(a.a3.like(quoteLike(keyword)))
        .build();
    Sort sort = orderBy(a.id.desc());
    return findAll(p, sort);
  }
}

interface AdvancedFooRepository {

  boolean editExample3();

  long removeExample4();

  List<A2Count> countExample5();
}

@Service
class AdvancedFooRepositoryImpl extends ShortcutExecute implements AdvancedFooRepository {

  @Override
  @Transactional
  public boolean editExample3() {
    QFoo a = QFoo.foo;
    Predicate p = predicate().and(a.id.eq(0L)).build();
    long rowsAffected = update(a)
        .set(a.createTime, a.createTime.add(1))
        .where(p)
        .execute();
    return rowsAffected > 0;
  }

  @Override
  @Transactional
  public long removeExample4() {
    QFoo a = QFoo.foo;
    Predicate p = predicate().and(a.createTime.lt(0L)).build();
    long rowsAffected = delete(a)
        .where(p)
        .execute();
    return rowsAffected;
  }

  @Override
  public List<A2Count> countExample5() {
    QFoo a = QFoo.foo;
    List<Tuple> tuples = findAll(
        q -> q
            .select(a.a2, Expressions.ONE.count())
            .from(a)
            .groupBy(a.a2));

    List<A2Count> list = new ArrayList<>();
    tuples.stream().forEach(tuple -> {
      A2Count m = new A2Count();
      m.setA2(tuple.get(a.a2));
      m.setCount(tuple.get(1, Long.class));
      list.add(m);
    });

    return list;
  }
}