package com.example.endpoint;

import org.springframework.boot.actuate.autoconfigure.ManagementContextConfiguration;
import org.springframework.boot.actuate.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@ManagementContextConfiguration
public class ContextTreeEndPointManagementContextConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnEnabledEndpoint("contexttree")
	public ContextTreeEndPoint contextTreeEndPoint() {
		ContextTreeEndPoint contextTreeEndPoint = new ContextTreeEndPoint();
		return contextTreeEndPoint;
	}

}
