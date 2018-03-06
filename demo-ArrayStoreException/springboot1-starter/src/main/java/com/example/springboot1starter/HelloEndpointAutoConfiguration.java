package com.example.springboot1starter;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
public class HelloEndpointAutoConfiguration {

	@ConditionalOnWebApplication
//	 @ImportAutoConfiguration(com.example.springboot1starter.HelloEndpoint.class)
	@Import(com.example.springboot1starter.HelloEndpoint.class)
	public static class ImportTest {

	}
}
