package com.example.springboot1starter;

import org.springframework.boot.actuate.autoconfigure.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.autoconfigure.EndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.HealthIndicatorAutoConfiguration;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(EndpointAutoConfiguration.class)
@AutoConfigureAfter(HealthIndicatorAutoConfiguration.class)
@ConditionalOnClass(value = { HealthIndicator.class })
// @ConditionalOnClass(value = {HealthIndicator.class, EndpointAutoConfiguration.class})
public class MyHealthIndicatorAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(MyHealthIndicator.class)
	@ConditionalOnEnabledHealthIndicator("my")
	public MyHealthIndicator myHealthIndicator() {
		return new MyHealthIndicator();
	}
}
