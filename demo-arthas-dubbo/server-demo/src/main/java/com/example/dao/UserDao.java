package com.example.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.User;

@Component
@Service
public class UserDao {

    private final SqlSession sqlSession;

    public UserDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public User selectUserById(long id) {
        return this.sqlSession.selectOne("selectUserById", id);
    }

    public List<User> allUsers() {
        return this.sqlSession.selectList("allUsers");
    }

}
