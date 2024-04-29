package com.origin.library.infrastructure.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import com.origin.library.infrastructure.jwt.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@Component
public class IdentityHandlerInterceptor implements HandlerInterceptor {

	final Logger logger = Logger.getLogger(IdentityHandlerInterceptor.class.getName());
	public static final String identityAttr = "Identity";

	@Autowired
	private JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		try {
			request.removeAttribute(identityAttr);
			String token = jwtService.extractToken(request);
			if (token == null || jwtService.isTokenExpired(token)) {
				return true;
			}

			String id = jwtService.extractId(token);
			request.setAttribute(identityAttr, id);
		} catch (Exception e) {
			logger.warning(e.getMessage());
			return true;
		}

		return true;
	}

	void addInterceptors(InterceptorRegistry registry) {
		List<String> guestPathPatterns = List.of("/api/login");

		registry.addInterceptor(this)
				.excludePathPatterns(guestPathPatterns);
	}
}