package com.example.demo.arthas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.arthas.aop.HelloWorldService;

@SpringBootApplication
public class DemoArthasApplication implements CommandLineRunner {

	// Simple example shows how an application can spy on itself with AOP
	@Autowired
	private HelloWorldService helloWorldService;

	@Override
	public void run(String... args) {
		System.out.println(this.helloWorldService.getHelloMessage());
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoArthasApplication.class, args);
	}

}
