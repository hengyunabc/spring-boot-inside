package com.example.demo.expected.single;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@SpringBootApplication
public class DemoExpectedSingleApplication {

	public static void main(String[] args) {
		// System.setProperty("debug", "true");
		SpringApplication.run(DemoExpectedSingleApplication.class, args);
	}

	@Bean
//	@Primary
	DataSource h2DataSource1() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
	}

	@Bean
	DataSource h2DataSource2() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
	}
}
