package com.origin.library.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.origin.library.domain.User;
import com.origin.library.domain.error.UserNotFoundError;
import com.origin.library.domain.error.UsernameOrPasswordError;
import com.origin.library.domain.success.Empty;
import com.origin.library.domain.success.Ok;
import com.origin.library.infrastructure.controller.BaseController;
import com.origin.library.infrastructure.controller.IdentityHandlerInterceptor;
import com.origin.library.infrastructure.jwt.JwtService;
import com.origin.library.infrastructure.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserController extends BaseController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	JwtService jwtService;

	@PostMapping("/api/login")
	public Ok<UserResource> login(
			@Valid LoginCommand command) throws UserNotFoundError, UsernameOrPasswordError, Exception {

		User user = userRepository.findByUsername(command.getUsername())
				.orElseThrow(() -> new UserNotFoundError().setDetails("username: " + command.getUsername()));

		if (!user.isMatchPassword(command.getPassword())) {
			throw new UsernameOrPasswordError().setDetails("username: " + command.getUsername());
		}

		UserResource response = UserResource.of(user);

		// Generate token and inject it into response headers
		String id = String.valueOf(user.getId());
		String token = jwtService.generateToken(id);

		HttpHeaders responseHeaders = new HttpHeaders();
		jwtService.injectToken(responseHeaders, token);
		responseHeaders.set(IdentityHandlerInterceptor.identityAttr, id);

		return Ok.of(response, responseHeaders);
	}

	@PostMapping("/api/logout")
	public Ok<Empty> logout() {
		// Client side should remove the token, so no need to do anything here
		return Ok.empty();
	}
}
