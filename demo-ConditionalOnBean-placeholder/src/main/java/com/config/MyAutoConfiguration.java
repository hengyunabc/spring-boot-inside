package com.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * load from META-INF/spring.factories
 *
 * @author hengyunabc 2017-02-14
 *
 */
@Configuration
public class MyAutoConfiguration {

	@Bean
	// if comment this line, it will be fine.
	@ConditionalOnBean(annotation = { MyAnnotation.class })
	public String abc() {
		return "abc";
	}
}
