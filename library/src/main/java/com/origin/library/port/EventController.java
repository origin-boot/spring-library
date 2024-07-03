package com.origin.library.port;

import org.springframework.stereotype.Component;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.origin.library.domain.event.UserLoginEvent;
import com.origin.library.infrastructure.redis.UserCount;

import java.util.logging.Logger;

@Component
public class EventController {

  private final Logger logger = Logger.getLogger(EventController.class.getName());

  public EventController(EventBus eventBus, AsyncEventBus asyncEventBus) {
    eventBus.register(this);
    asyncEventBus.register(this);
  }

  @Subscribe
  public void handleUserLoginEvent(UserLoginEvent event) {
    logger.info("User login event: " + event.getUserId());
    new UserCount(event.getUserId()).increaseLoginCount();
  }

}
