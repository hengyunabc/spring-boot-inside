package com.example.demo.arthas;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminFilterConfig {

	@Bean
	FilterRegistrationBean testFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new AdminFilter());
		filterRegistrationBean.setUrlPatterns(Arrays.asList("/admin/*"));
		return filterRegistrationBean;
	}

	static class AdminFilter implements javax.servlet.Filter {

		@Override
		public void init(FilterConfig filterConfig) throws ServletException {

		}

		@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
				throws IOException, ServletException {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "admin filter error.");
		}

		@Override
		public void destroy() {

		}

	}

}
