package com.origin.library.infrastructure.redis;

import org.springframework.data.redis.core.RedisTemplate;

import com.origin.library.infrastructure.util.ApplicationContextUtil;

import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.util.Optional;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ShortcutOperator {
  private final Logger logger = Logger.getLogger(ShortcutOperator.class.getName());

  private RedisTemplate<String, Object> redisTemplate;

  private static String globalPrefix = "";
  private String sessionPrefix = "";
  private boolean isInitialized = false;

  public void initConnection() {
    if (isInitialized) {
      return;
    }
    isInitialized = true;
    initRedisTemplate();
    logger.log(Level.FINE, getClass().getSimpleName() + " init connection of Redis");
  }

  private void initRedisTemplate() {
    @SuppressWarnings("unchecked")
    RedisTemplate<String, Object> redisTemplate = ApplicationContextUtil
        .getBean("stringRedisTemplate", RedisTemplate.class);
    setRedisTemplate(redisTemplate);
  }

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
    logger.log(Level.FINE, String.format("Redis Command: SET %s %s EX %d",
        getFullKey(key), value, timeout));
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
    logger.log(Level.FINE, String.format("Redis Command: SETNX %s %s EX %d",
        getFullKey(key), value, timeout));
    if (timeout == 0) {
      redisTemplate.opsForValue().setIfAbsent(getFullKey(key), value);
      return;
    }
    redisTemplate.opsForValue().setIfAbsent(getFullKey(key), value, timeout, TimeUnit.SECONDS);
  }

  public Optional<Object> get(String key) {
    Object value = redisTemplate.opsForValue().get(getFullKey(key));
    logger.log(Level.FINE, String.format("Redis Command: GET %s, RESULT: %s",
        getFullKey(key), value));
    return Optional.ofNullable(value);
  }

  public boolean hasKey(String key) {
    return redisTemplate.hasKey(getFullKey(key));
  }

  public long increment(String key, long delta) {
    long value = redisTemplate.opsForValue().increment(getFullKey(key), delta);
    logger.log(Level.FINE, String.format("Redis Command: INCRBY %s %d, RESULT: %d",
        getFullKey(key), delta, value));
    return value;
  }

  public long decrement(String key, long delta) {
    long value = redisTemplate.opsForValue().decrement(getFullKey(key), delta);
    logger.log(Level.FINE, String.format("Redis Command: DECRBY %s %d, RESULT: %d",
        getFullKey(key), delta, value));
    return value;
  }

  public long size(String key) {
    long value = redisTemplate.opsForValue().size(getFullKey(key));
    logger.log(Level.FINE, String.format("Redis Command: STRLEN %s, RESULT: %d",
        getFullKey(key), value));
    return value;
  }

  public void expire(String key, long timeout) {
    redisTemplate.expire(getFullKey(key), timeout, TimeUnit.SECONDS);
    logger.log(Level.FINE, String.format("Redis Command: EXPIRE %s %d",
        getFullKey(key), timeout));
  }

  public void expireAt(String key, long timestamp) {
    redisTemplate.expireAt(getFullKey(key), new Date(timestamp));
    logger.log(Level.FINE, String.format("Redis Command: EXPIREAT %s %d",
        getFullKey(key), timestamp));
  }

  public long getExpire(String key) {
    long value = redisTemplate.getExpire(getFullKey(key), TimeUnit.SECONDS);
    logger.log(Level.FINE, String.format("Redis Command: TTL %s, RESULT: %d",
        getFullKey(key), value));
    return value;
  }

  public boolean delete(String key) {
    boolean removed = redisTemplate.delete(getFullKey(key));
    logger.log(Level.FINE, String.format("Redis Command: DEL %s, RESULT: %b",
        getFullKey(key), removed));
    return removed;
  }

  public Optional<Map<? extends Object, ? extends Object>> hashGetEntries(String key) {
    Map<? extends Object, ? extends Object> value = redisTemplate.opsForHash()
        .entries(getFullKey(key));
    logger.log(Level.FINE, String.format("Redis Command: HGETALL %s, RESULT: %s",
        getFullKey(key), value));
    return Optional.ofNullable(value);
  }

  public void hashPutEntries(String key, Map<? extends Object, ? extends Object> entries) {
    redisTemplate.opsForHash().putAll(getFullKey(key), entries);
    logger.log(Level.FINE, String.format("Redis Command: HMSET %s %s",
        getFullKey(key), entries));
  }

  public Optional<Object> hashGet(String key, String hashKey) {
    Object value = redisTemplate.opsForHash().get(getFullKey(key), hashKey);
    logger.log(Level.FINE, String.format("Redis Command: HGET %s %s, RESULT: %s",
        getFullKey(key), hashKey, value));
    return Optional.ofNullable(value);
  }

  public void hashSet(String key, String hashKey, Object value) {
    redisTemplate.opsForHash().put(getFullKey(key), hashKey, value);
    logger.log(Level.FINE, String.format("Redis Command: HSET %s %s %s",
        getFullKey(key), hashKey, value));
  }

  public boolean hashHasKey(String key, String hashKey) {
    boolean value = redisTemplate.opsForHash().hasKey(getFullKey(key), hashKey);
    logger.log(Level.FINE, String.format("Redis Command: HEXISTS %s %s, RESULT: %b",
        getFullKey(key), hashKey, value));
    return value;
  }

  public long hashIncrement(String key, String hashKey, long delta) {
    long value = redisTemplate.opsForHash().increment(getFullKey(key), hashKey, delta);
    logger.log(Level.FINE, String.format("Redis Command: HINCRBY %s %s %d, RESULT: %d",
        getFullKey(key), hashKey, delta, value));
    return value;
  }

  public long hashDecrement(String key, String hashKey, long delta) {
    long value = redisTemplate.opsForHash().increment(getFullKey(key), hashKey, -delta);
    logger.log(Level.FINE, String.format("Redis Command: HINCRBY %s %s %d, RESULT: %d",
        getFullKey(key), hashKey, -delta, value));
    return value;
  }

  public long hashSize(String key) {
    long value = redisTemplate.opsForHash().size(getFullKey(key));
    logger.log(Level.FINE, String.format("Redis Command: HLEN %s, RESULT: %d",
        getFullKey(key), value));
    return value;
  }

  public boolean hashDelete(String key, String... hashKeys) {
    boolean deleted = redisTemplate.opsForHash().delete(getFullKey(key), (Object[]) hashKeys) > 0;
    logger.log(Level.FINE, String.format("Redis Command: HDEL %s %s, RESULT: %b",
        getFullKey(key), String.join("/", hashKeys), deleted));
    return deleted;
  }

  // FIXME: add list/zset/zrange operations
}
