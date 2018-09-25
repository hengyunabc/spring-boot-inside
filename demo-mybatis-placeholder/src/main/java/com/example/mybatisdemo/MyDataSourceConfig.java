package com.example.mybatisdemo;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyDataSourceConfig {
	@Bean(name = "dataSource1")
	public DataSource dataSource1(@Value("${db.user}") String user) {
		System.err.println("user: " + user);
		JdbcDataSource ds = new JdbcDataSource();
		ds.setURL("jdbc:h2:˜/test");
		ds.setUser(user);
		return ds;
	}
}
