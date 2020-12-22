package com.example.serverdemo;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.User;
import com.example.dao.UserDao;

@SpringBootApplication(scanBasePackages = "com.example.dao")
public class ServerDemoApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ServerDemoApplication.class, args);

        UserDao dao = context.getBean(UserDao.class);

        List<User> allUsers = dao.allUsers();

        System.err.println(allUsers);
    }

}
