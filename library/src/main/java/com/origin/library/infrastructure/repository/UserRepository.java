package com.origin.library.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.origin.library.domain.Page;
import com.origin.library.domain.QUser;
import com.origin.library.domain.User;
import com.origin.library.infrastructure.querydsl.ShortcutExecute;
import com.origin.library.infrastructure.querydsl.ShortcutPredicateExecutor;
import com.querydsl.core.types.Predicate;


public interface UserRepository
    extends JpaRepository<User, Long>, ShortcutPredicateExecutor<User>, AdvancedUserRepository {
  Optional<User> findByUsername(String username);

  // FIXME: remove the example
  default Page<User> searchUsers(String keyword, int pageNumber, int pageSize) {
    QUser a = QUser.user;
    Predicate p = predicate()
        .and(isNotBlank(keyword), a.username.like(quoteLike(keyword)))
        .build();
    Sort sort = asc("id");
    return findAll(p, sort, pageNumber, pageSize);
  }
}

interface AdvancedUserRepository {
  Page<User> searchUsers(String keyword, int pageSize);

  boolean updateUserCreateTime(long userId);

  long deleteUserBeforeTime(long createTime);
}

@Service
class AdvancedUserRepositoryImpl extends ShortcutExecute implements AdvancedUserRepository {
  // FIXME: remove the example
  @Override
  public Page<User> searchUsers(String keyword, int pageSize) {
    QUser a = QUser.user;
    // where username like '%user%'
    // and id>0 and (id>0 and id<1000) and (id>-1 or id<1001)
    // or id<0 or (id<-1 or id<-2) or (id<-3 and id<-4)
    Predicate p = predicate()
        .and(isNotBlank(keyword), a.username.like(quoteLike(keyword)))
        .and(a.id.gt(0))
        .and(a.id.gt(0), a.id.lt(1000))
        .andAnyOf(true, a.id.gt(-1), a.id.lt(1001))
        .or(a.id.lt(0))
        .or(a.id.lt(-1), a.id.lt(-2))
        .orAllOf(true, a.id.lt(-3), a.id.lt(-4))
        .build();
    Page<User> r = findAll(
        q -> q.select(a)
            .from(a)
            .where(p),
        pageSize);
    return r;
  }

  // FIXME: remove the example
  @Override
  @Transactional
  public boolean updateUserCreateTime(long userId) {
    QUser a = QUser.user;
    Predicate p = predicate().and(a.id.eq(userId)).build();
    long rowsAffected = update(a)
        .set(a.createTime, a.createTime.add(1))
        .where(p)
        .execute();
    return rowsAffected > 0;
  }

  // FIXME: remove the example
  @Override
  @Transactional
  public long deleteUserBeforeTime(long createTime) {
    QUser a = QUser.user;
    Predicate p = predicate().and(a.createTime.lt(createTime)).build();
    long rowsAffected = delete(a)
        .where(p)
        .execute();
    return rowsAffected;
  }
}