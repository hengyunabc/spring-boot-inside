package com.example.ArrayStoreExceptionDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.springboot1starter.MyHealthIndicatorAutoConfiguration;

@SpringBootApplication
public class ArrayStoreExceptionDemoApplication {

	public static void main(String[] args) {
//		try {
//			MyHealthIndicatorAutoConfiguration.class.getDeclaredAnnotation(Endpoint.class);
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
		SpringApplication.run(ArrayStoreExceptionDemoApplication.class, args);
	}
}
