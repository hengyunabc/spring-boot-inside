package com.example.democlassloadercontext;

import java.net.URLClassLoader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.democlassloadercontext.utils.ClassLoaderUtils;

@SpringBootApplication
public class DemoClassloaderContextApplication {

	public static void main(String[] args) {
		System.out.println("========= ClassLoader Tree=============");
		System.out.println(ClassLoaderUtils.tree());

		System.out.println("========= Spring Boot Application ClassLoader Urls =============");
		System.out.println(ClassLoaderUtils.urls((URLClassLoader) DemoClassloaderContextApplication.class.getClassLoader()));

		System.out.println("========= System ClassLoader Urls =============");
		System.out.println(ClassLoaderUtils.urls((URLClassLoader) ClassLoader.getSystemClassLoader()));

		SpringApplication.run(DemoClassloaderContextApplication.class, args);
	}
}
