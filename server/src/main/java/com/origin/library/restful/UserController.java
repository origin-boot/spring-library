package com.origin.library.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.origin.library.domain.User;
import com.origin.library.domain.error.UserNotFoundError;
import com.origin.library.domain.error.UsernameOrPasswordError;
import com.origin.library.domain.success.Empty;
import com.origin.library.domain.success.Ok;
import com.origin.library.infrastructure.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;

	@PostMapping("/api/login")
	public Ok<UserResource> login(
			@Valid LoginCommand command) throws UserNotFoundError, UsernameOrPasswordError {

		User user = userRepository.findByUsername(command.getUsername())
				.orElseThrow(() -> new UserNotFoundError().setDetails("username: " + command.getUsername()));

		if (!user.isMatchPassword(command.getPassword())) {
			throw new UsernameOrPasswordError().setDetails("username: " + command.getUsername());
		}

		UserResource response = UserResource.of(user);

		return Ok.of(response);
	}

	@PostMapping("/api/logout")
	public Ok<Empty> logout() {

		return Ok.empty();
	}
}
