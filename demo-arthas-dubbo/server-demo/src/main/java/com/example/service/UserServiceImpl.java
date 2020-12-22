package com.example.service;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.User;
import com.example.dao.UserDao;

@DubboService(version = "1.0.0")
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public User findUser(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("user id < 1, id: " + id);
        }
        User user = userDao.selectUserById(id);
        if (user != null) {
            return user;
        }

        throw new RuntimeException("Can not find user, id: " + id);
    }

    @Override
    public List<User> listUsers() {
        return userDao.allUsers();
    }

    @Override
    public User findUserByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }

}
