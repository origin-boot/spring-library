package com.origin.library.infrastructure.test;

import org.springframework.beans.factory.annotation.Autowired;

import com.origin.library.domain.User;
import com.origin.library.domain.error.UserNotFoundError;
import com.origin.library.infrastructure.repository.UserRepository;

public class ShortcutTester {
  @Autowired
  private UserRepository userRepository;

  private static final long firstUserId = 1L;
  private static final long secondUserId = 2L;

  public User getUser(long id) throws Exception {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new UserNotFoundError().setDetails("id: " + id));
  }

  public User getFirstUser() throws Exception {
    return getUser(firstUserId);
  }

  public User getSecondUser() throws Exception {
    return getUser(secondUserId);
  }

  public interface Handler {
    // FIXME: use the generic type T to replace the universal hammer Object
    Object handle() throws Exception;
  }

  @SuppressWarnings("unchecked")
  public <T> Exceptional<T> execute(Handler handler) {
    Exceptional<T> exceptional = new Exceptional<>();
    try {
      exceptional.value = (T) handler.handle();
    } catch (Exception e) {
      exceptional.exception = e;
    }
    return exceptional;
  }
}
