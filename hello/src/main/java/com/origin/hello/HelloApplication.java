package com.origin.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.origin.library.infrastructure.util.TimeUtil;

@SpringBootApplication(scanBasePackages = { "com.origin.library.infrastructure" })
@RestController
public class HelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@GetMapping("/api/hello")
	public String hello() {
		long timestamp = TimeUtil.getUnixTimestamp();
		// long timestamp = 0;
		return "Hello, World!\n" + "Timestamp: " + timestamp;
	}
}
