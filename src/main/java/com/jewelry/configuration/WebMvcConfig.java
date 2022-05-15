package com.jewelry.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jewelry.controller.LoggingHandlerInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Autowired
	private LoggingHandlerInterceptor loggingHandlerInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//		registry.addInterceptor(loggingHandlerInterceptor);
	}

}
