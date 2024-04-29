package com.origin.library.infrastructure.controller;

import com.origin.library.domain.User;
import com.origin.library.domain.error.UserNotFoundError;

import jakarta.servlet.http.HttpServletRequest;

public class BaseController {

	protected User getLoginUser(HttpServletRequest request) throws UserNotFoundError {
		Object value = request.getAttribute(UserHandlerInterceptor.userAttr);
		if (value == null) {
			throw new UserNotFoundError().setDetails("find user from http request");
		}
		return (User) value;
	}
}
