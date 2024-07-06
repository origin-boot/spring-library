package com.origin.library.port;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.eventbus.AsyncEventBus;
import com.origin.library.domain.User;
import com.origin.library.domain.error.UserNotFoundError;
import com.origin.library.domain.error.UsernameOrPasswordError;
import com.origin.library.domain.event.UserLoginEvent;
import com.origin.library.domain.success.Empty;
import com.origin.library.domain.success.Ok;
import com.origin.library.infrastructure.repository.UserRepository;
import com.origin.library.port.control.BaseController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
public class UserController extends BaseController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AsyncEventBus asyncEventBus;

  @PostMapping("/api/login")
  public Ok<UserResource> login(
      HttpServletResponse httpServletResponse,
      @Valid LoginCommand command)
      throws UserNotFoundError, UsernameOrPasswordError, Exception {

    User user = userRepository.findByUsername(command.getUsername())
        .orElseThrow(() -> new UserNotFoundError()
            .setDetails("username: " + command.getUsername()));

    if (!user.isMatchPassword(command.getPassword())) {
      throw new UsernameOrPasswordError().setDetails("username: " + command.getUsername());
    }

    identityHandlerInterceptor.save(httpServletResponse, String.valueOf(user.getId()));

    // Publish user login event
    asyncEventBus.post(new UserLoginEvent(user.getId()));
    // userRepository.editUserCreateTime(user.getId());
    // userRepository.removeUserBeforeTime(1);
    // userRepository.searchBeforeEdit(true);

    UserResource response = UserResource.of(user);
    return Ok.of(response);
  }

  @PostMapping("/api/logout")
  public Ok<Empty> logout() {
    // Client side should remove the token, so no need to do anything here
    return Ok.empty();
  }
}
