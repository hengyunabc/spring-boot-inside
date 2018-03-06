package com.example.springboot1starter;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "endpoints.health")
public class HelloEndpoint extends AbstractEndpoint<String> {

	public HelloEndpoint() {
		super("hello", false);
	}

	@Override
	public String invoke() {
		return "hello";
	}

}