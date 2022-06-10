package com.fteam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fteam.interceptor.AuthInterceptor;
import com.fteam.interceptor.CategoryInterceptor;
import com.fteam.interceptor.StaffManagementInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Autowired
	private CategoryInterceptor categoryInterceptor;

	@Autowired
	private AuthInterceptor auth;

	@Autowired
	private StaffManagementInterceptor staffManagementInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(categoryInterceptor) //
				.addPathPatterns("/**", "/admin/**") //
				.excludePathPatterns("/assets/**");

		registry.addInterceptor(auth) //
				.addPathPatterns("/order**", "/admin/**") //
				.excludePathPatterns("/assets/**", "/admin/login");

		registry.addInterceptor(staffManagementInterceptor) //
				.addPathPatterns("/admin/staff/**") //
				.excludePathPatterns("/assets/**", "/admin/login");
	}

}
