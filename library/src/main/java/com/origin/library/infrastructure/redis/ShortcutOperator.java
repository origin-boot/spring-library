package com.origin.library.infrastructure.redis;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.util.Optional;
import java.util.Map;

public abstract class ShortcutOperator {
  private RedisTemplate<String, Object> redisTemplate;

  private static String globalPrefix = "";
  private String sessionPrefix = "";

  protected void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public static void setGlobalPrefix(String prefix) {
    globalPrefix = prefix;
  }

  protected void setSessionPrefix(String prefix) {
    this.sessionPrefix = prefix;
  }

  private String getFullKey(String key) {
    return globalPrefix + sessionPrefix + key;
  }

  public void set(String key, Object value, long timeout) {
    if (timeout < 0) {
      throw new IllegalArgumentException("redis timeout must be greater than or equal to 0");
    }
    if (timeout == 0) {
      redisTemplate.opsForValue().set(getFullKey(key), value);
      return;
    }
    redisTemplate.opsForValue().set(getFullKey(key), value, timeout, TimeUnit.SECONDS);
  }

  public void setIfAbsent(String key, Object value, long timeout) {
    if (timeout < 0) {
      throw new IllegalArgumentException("redis timeout must be greater than or equal to 0");
    }
    if (timeout == 0) {
      redisTemplate.opsForValue().setIfAbsent(getFullKey(key), value);
      return;
    }
    redisTemplate.opsForValue().setIfAbsent(getFullKey(key), value, timeout, TimeUnit.SECONDS);
  }

  public Optional<Object> get(String key) {
    Object value = redisTemplate.opsForValue().get(getFullKey(key));
    return Optional.ofNullable(value);
  }

  public boolean delete(String key) {
    return redisTemplate.delete(getFullKey(key));
  }

  public boolean hasKey(String key) {
    return redisTemplate.hasKey(getFullKey(key));
  }

  public long increment(String key, long delta) {
    return redisTemplate.opsForValue().increment(getFullKey(key), delta);
  }

  public long decrement(String key, long delta) {
    return redisTemplate.opsForValue().decrement(getFullKey(key), delta);
  }

  public long size(String key) {
    return redisTemplate.opsForValue().size(getFullKey(key));
  }

  public void expire(String key, long timeout) {
    redisTemplate.expire(getFullKey(key), timeout, TimeUnit.SECONDS);
  }

  public void expireAt(String key, long timestamp) {
    redisTemplate.expireAt(getFullKey(key), new Date(timestamp));
  }

  public long getExpire(String key) {
    return redisTemplate.getExpire(getFullKey(key), TimeUnit.SECONDS);
  }

  public Optional<Map<? extends Object, ? extends Object>> hashGetEntries(String key) {
    Map<? extends Object, ? extends Object> value = redisTemplate.opsForHash()
        .entries(getFullKey(key));
    return Optional.ofNullable(value);
  }

  public void hashPutEntries(String key, Map<? extends Object, ? extends Object> entries) {
    redisTemplate.opsForHash().putAll(getFullKey(key), entries);
  }

  public Optional<Object> hashGet(String key, String hashKey) {
    Object value = redisTemplate.opsForHash().get(getFullKey(key), hashKey);
    return Optional.ofNullable(value);
  }

  public void hashSet(String key, String hashKey, Object value) {
    redisTemplate.opsForHash().put(getFullKey(key), hashKey, value);
  }

  public boolean hashDelete(String key, Object... hashKeys) {
    return redisTemplate.opsForHash().delete(getFullKey(key), hashKeys) > 0;
  }

  public boolean hashHasKey(String key, String hashKey) {
    return redisTemplate.opsForHash().hasKey(getFullKey(key), hashKey);
  }

  public long hashIncrement(String key, String hashKey, long delta) {
    return redisTemplate.opsForHash().increment(getFullKey(key), hashKey, delta);
  }

  public long hashDecrement(String key, String hashKey, long delta) {
    return redisTemplate.opsForHash().increment(getFullKey(key), hashKey, -delta);
  }

  public long hashSize(String key) {
    return redisTemplate.opsForHash().size(getFullKey(key));
  }

  public boolean zsetAdd(String key, Object value, double score) {
    return redisTemplate.opsForZSet().add(getFullKey(key), value, score);
  }

  public boolean zsetRemove(String key, Object... values) {
    return redisTemplate.opsForZSet().remove(getFullKey(key), values) > 0;
  }

  public long zsetSize(String key) {
    return redisTemplate.opsForZSet().size(getFullKey(key));
  }

  public long zsetRank(String key, Object value) {
    return redisTemplate.opsForZSet().rank(getFullKey(key), value);
  }

  public long zsetReverseRank(String key, Object value) {
    return redisTemplate.opsForZSet().reverseRank(getFullKey(key), value);
  }

  public double zsetScore(String key, Object value) {
    return redisTemplate.opsForZSet().score(getFullKey(key), value);
  }

  // FIXME: add list/zrange operations
}
