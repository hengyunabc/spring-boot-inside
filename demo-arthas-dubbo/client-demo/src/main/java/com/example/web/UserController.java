package com.example.web;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.User;
import com.example.UserService;

@RestController
public class UserController {

    @DubboReference(version = "1.0.0", url = "dubbo://127.0.0.1:12345")
	private UserService userService;

	@GetMapping("/user/{id}")
	public User findUserById(@PathVariable Integer id) {
		return userService.findUser(id);
	}

	@GetMapping("/users")
	public List<User> users() {
		return userService.listUsers();
	}
}
