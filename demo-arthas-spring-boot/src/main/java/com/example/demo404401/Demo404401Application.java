package com.example.demo404401;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo404401.aop.HelloWorldService;

@SpringBootApplication
public class Demo404401Application implements CommandLineRunner {

	// Simple example shows how an application can spy on itself with AOP
	@Autowired
	private HelloWorldService helloWorldService;

	@Override
	public void run(String... args) {
		System.out.println(this.helloWorldService.getHelloMessage());
	}

	public static void main(String[] args) {
		SpringApplication.run(Demo404401Application.class, args);
	}

}
