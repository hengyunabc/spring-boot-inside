package com.example.service;

import java.util.List;

import com.example.User;

public interface UserService {

	public User findUser(int id);

	public List<User> listUsers();

	public User findUserByName(String name);

}
