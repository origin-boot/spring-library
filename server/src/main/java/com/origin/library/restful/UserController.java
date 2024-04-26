package com.origin.library.restful;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	// FIXME: wrapper any exception to ResponseEntity with status code 4xx/5xx
	// handle exception in global exception handler
	// handle 404 not found
	// handle 405 method not allowed
	// handle other exception

	@PostMapping("/api/login")
	public ResponseEntity<Void> login(
			@RequestParam("username") final String username,
			@RequestParam("password") final String password) {

		// FIXME: Implement login logic and save user session
		// could be HttpSession or JWT token

		return ResponseEntity.ok().build();
	}

	@PostMapping("/api/logout")
	public ResponseEntity<Void> logout() {

		// FIXME: Implement logout logic and clear user session
		
		return ResponseEntity.ok().build();
	}
}
