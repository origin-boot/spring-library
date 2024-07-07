package com.origin.library.infrastructure.redis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShortcutOperatorTest extends ShortcutOperator {

  @BeforeEach
  public void contextLoads() throws Exception {
    initConnection();
  }

  @Test
  public void testString() {
    String key = "foo";
    String value = "foo_value";
    long timeout = 10;

    set(key, value, timeout);
    Object obj = get(key).get();
    assertNotNull(obj);
    assertEquals(value, obj);

    boolean hasKey = hasKey(key);
    assertEquals(true, hasKey);

    setIfAbsent(key, value, timeout);
    long expire = getExpire(key);
    assertNotEquals(0, expire);

    long size = size(key);
    assertNotEquals(0, size);

    expire(key, timeout);

    expireAt(key, System.currentTimeMillis() + timeout * 1000);

    String numKey = "num";

    boolean deleted = delete(numKey);
    assertNotNull(deleted);

    long increment = increment(numKey, 1);
    assertEquals(1, increment);

    long decrement = decrement(numKey, 1);
    assertEquals(0, decrement);

    delete(key);
    delete(numKey);
  }

  @Test
  public void testHash() {
    String key = "hash";
    Map<String, String> value = new HashMap<>() {
      {
        put("key1", "value1");
        put("key2", "value2");
        put("num", "1");
      }
    };
    hashPutEntries(key, value);

    Optional<Map<? extends Object, ? extends Object>> savedValue = hashGetEntries(key);
    assertNotNull(savedValue);
    assertEquals(value, savedValue.get());

    String key1Value = (String) hashGet(key, "key1").get();
    assertNotNull(key1Value);
    assertEquals("value1", key1Value);

    boolean hasKey = hashHasKey(key, "key1");
    assertEquals(true, hasKey);

    hashSet(key, "key1", "value1_1");
    String key1Value1 = (String) hashGet(key, "key1").get();
    assertNotNull(key1Value1);
    assertEquals("value1_1", key1Value1);

    long size = hashSize(key);
    assertEquals(3, size);

    long increment = hashIncrement(key, "num", 1);
    assertEquals(2, increment);

    long decrement = hashDecrement(key, "num", 1);
    assertEquals(1, decrement);

    boolean deleted = hashDelete(key, "key2");
    assertEquals(true, deleted);

    delete(key);
  }
}
