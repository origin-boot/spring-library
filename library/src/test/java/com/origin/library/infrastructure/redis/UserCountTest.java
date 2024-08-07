package com.origin.library.infrastructure.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserCountTest {
  @Test
  public void testGetLoginCount() {
    UserCount userCount = new UserCount(1L);
    userCount.increaseLoginCount();
    assert userCount.getLoginCount() > 0;
  }
}
