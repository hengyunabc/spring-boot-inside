package com.example.demo.arthas.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@GetMapping("/user/{id}")
	public User findUserById(@PathVariable Integer id) {
		logger.info("id: {}" , id);

		if (id != null && id < 1) {
			return new User(id, "name" + id);
			// throw new IllegalArgumentException("id < 1");
		} else {
			return new User(id, "name" + id);
		}
	}

}
