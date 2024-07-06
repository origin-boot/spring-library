package com.origin.library.port;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.origin.library.domain.success.Ok;
import com.origin.library.infrastructure.test.Exceptional;
import com.origin.library.infrastructure.test.ShortcutTester;

@SpringBootTest
public class UserControllerTest extends ShortcutTester {

  @Autowired
  private UserController userController;

  @BeforeEach
  public void contextLoads() throws Exception {
    assertNotNull(userController);
  }

  @Test
  public void testLogin() {
    MockHttpServletResponse response = new MockHttpServletResponse();
    LoginCommand loginCommand = new LoginCommand();
    loginCommand.setUsername("user1");
    loginCommand.setPassword("password1");

    Exceptional<Ok<UserResource>> e = execute(
        () -> userController.login(response, loginCommand));
    assertNull(e.getException());
    UserResource user = e.getValue().getBody();
    assertEquals(loginCommand.getUsername(), user.getUsername());
    assertEquals(1L, user.getId());
  }

  @Test
  public void testLogout() {
    Exceptional<Void> e = execute(() -> userController.logout());
    assertNull(e.getException());
  }
}
