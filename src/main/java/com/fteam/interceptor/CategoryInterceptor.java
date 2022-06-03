package com.fteam.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fteam.repository.CategoryRepository;

@Service
public class CategoryInterceptor implements HandlerInterceptor {

	@Autowired
	private CategoryRepository repo;
	
	@Override
	public void postHandle( //
			HttpServletRequest request, //
			HttpServletResponse response, //
			Object handler, //
			ModelAndView modelAndView) //
			throws Exception //
	{
		request.setAttribute("categories", repo.findAll());
	}
	
}
