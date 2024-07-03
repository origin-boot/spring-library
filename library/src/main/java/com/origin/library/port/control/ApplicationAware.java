package com.origin.library.port.control;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.origin.library.infrastructure.util.ApplicationContextUtil;

@Component
public class ApplicationAware implements ApplicationContextAware {
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    ApplicationContextUtil.setApplicationContext(applicationContext);
  }
}