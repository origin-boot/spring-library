package com.origin.library.infrastructure.redis;

import java.util.Optional;

public class UserCount extends ShortcutOperator implements RedisCacher {
  private long userId;

  private static final String LOGIN_COUNT = "loginCount";

  public UserCount(long userId) {
    this.initConnection();
    this.userId = userId;
  }

  @Override
  public String getKey() {
    return "users:" + userId;
  }

  @Override
  public long getTimeout() {
    return 0;
  }

  public long getLoginCount() {
    Optional<Object> optional = hashGet(getKey(), LOGIN_COUNT);
    if (!optional.isPresent()) {
      return 0;
    }
    String value = (String) optional.get();
    return Long.parseLong(value);
  }

  public void increaseLoginCount() {
    hashIncrement(getKey(), LOGIN_COUNT, 1);
  }
}
