package com.example.demoNoSuchMethodError;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoNoSuchMethodErrorApplication {

	public static void main(String[] args) throws IOException {
		try {
			SpringApplication.run(DemoNoSuchMethodErrorApplication.class, args);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		// block
		System.in.read();
	}
}
