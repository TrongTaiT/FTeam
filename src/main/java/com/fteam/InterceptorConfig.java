package com.fteam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fteam.interceptor.AuthInterceptor;
import com.fteam.interceptor.CategoryInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Autowired
	private CategoryInterceptor categoryInterceptor;
	@Autowired
	AuthInterceptor auth;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(categoryInterceptor) //
				.addPathPatterns("/**") //
				.excludePathPatterns("/assets/**", "/images/**");
		
		registry.addInterceptor(auth)//
				.addPathPatterns("/order**", "/admin/**")
				.excludePathPatterns("/assets/**", "/admin/login");
	}
	
	

}
