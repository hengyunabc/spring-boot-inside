package com.example;

import java.util.List;

public interface UserService {

	public User findUser(int id);

	public List<User> listUsers();

	public User findUserByName(String name);

}
