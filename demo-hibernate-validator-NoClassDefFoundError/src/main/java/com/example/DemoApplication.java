package com.example;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws IOException {
//		simpleWay();
		SpringApplication.run(DemoApplication.class, args);
	}

	public static void simpleWay() throws IOException {
		try {
			org.hibernate.validator.internal.util.Version.touch();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		System.in.read();

		try {
			org.hibernate.validator.internal.util.Version.touch();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
