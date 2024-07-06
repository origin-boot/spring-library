package com.origin.library.port.control;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpServletResponse;

public class BaseController {

  @Autowired
  private IdentityHandlerInterceptor identityHandlerInterceptor;

  protected void saveIdentity(HttpServletResponse httpServletResponse, String id) throws Exception {
    identityHandlerInterceptor.save(httpServletResponse, id);
  }

  // FIXME: other controllers should extend this class, add common methods here
}
