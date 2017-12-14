package com.example.demo.transactional.nullpointerexception;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import sample.mybatis.dao.StudentDao;

@SpringBootApplication(scanBasePackages = { "sample.mybatis" })
public class DemoNullPointerExceptionApplication {

	@Autowired
	private StudentDao studentDao;

	public static void main(String[] args) {
		// System.setProperty("debug", "true");
		SpringApplication.run(DemoNullPointerExceptionApplication.class, args);
	}

	@PostConstruct
	public void init() {
		System.err.println(studentDao.getClass());
		studentDao.selectStudentById(1);
		studentDao.finalSelectStudentById(1);
	}

	@Bean
	@Primary
	DataSource h2DataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScript("classpath:schema-dev.sql")
				.build();
	}

}
