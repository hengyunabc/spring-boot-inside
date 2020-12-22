package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;

import com.github.javafaker.Faker;

@DubboService(version = "1.0.0")
public class UserServiceImpl implements UserService {
	private static List<User> users = new ArrayList<>();

	static {
		for (int i = 1; i < 1024; ++i) {
			Faker faker = new Faker();
			users.add(new User(i, faker.name().fullName()));
		}
	}

	@Override
	public User findUser(int id) {
		if (id < 1) {
			throw new IllegalArgumentException("user id < 1, id: " + id);
		}
		for (User user : users) {
			if (user.getId() == id) {
				return user;
			}
		}
		throw new RuntimeException("Can not find user, id: " + id);
	}

	@Override
	public List<User> listUsers() {
		return Collections.unmodifiableList(users);
	}

	@Override
	public User findUserByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
