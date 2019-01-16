package com.example.demo404401.jsp;

import org.apache.catalina.Context;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author hengyunabc 2017-07-29
 *
 */
@Configuration
@ConditionalOnProperty(name = "tomcat.staticResourceCustomizer.enabled", matchIfMissing = true)
public class TomcatConfiguration {
	@Bean
	public EmbeddedServletContainerCustomizer staticResourceCustomizer() {
		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				if (container instanceof TomcatEmbeddedServletContainerFactory) {
					((TomcatEmbeddedServletContainerFactory) container)
							.addContextCustomizers(new TomcatContextCustomizer() {
								@Override
								public void customize(Context context) {
									context.addLifecycleListener(new StaticResourceConfigurer(context));
								}
							});
				}
			}

		};
	}
}
