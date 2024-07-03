package com.origin.library.infrastructure.task;

import java.util.logging.Logger;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DummyTask {
  private final Logger logger = Logger.getLogger(DummyTask.class.getName());

  @Scheduled(cron = "0 0 0 * * ?")
  public void dummyTask() {
    logger.info("Dummy task is running");
  }
}
