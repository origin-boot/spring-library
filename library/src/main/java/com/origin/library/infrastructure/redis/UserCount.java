package com.origin.library.infrastructure.redis;

import java.util.Optional;
import org.springframework.data.redis.core.RedisTemplate;

import com.origin.library.infrastructure.util.ApplicationContextUtil;

public class UserCount extends ShortcutOperator implements RedisCacher {
  private long userId;

  private static final String LOGIN_COUNT = "loginCount";

  public UserCount(long userId) {
    this.init();
    this.userId = userId;
  }

  @Override
  public void init() {
    @SuppressWarnings("unchecked")
    RedisTemplate<String, Object> redisTemplate = ApplicationContextUtil
        .getBean("redisTemplate", RedisTemplate.class);
    super.setRedisTemplate(redisTemplate);
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

  public void setLoginCount(long count) {
    hashSet(getKey(), LOGIN_COUNT, String.valueOf(count));
  }
}
