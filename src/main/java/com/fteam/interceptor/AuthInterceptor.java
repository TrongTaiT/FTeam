package com.fteam.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fteam.model.Staff;
import com.fteam.utilities.SessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

@Service
public class AuthInterceptor implements HandlerInterceptor{

	@Autowired
	SessionService session;
	
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception{		
		String uri = req.getRequestURI().substring(req.getContextPath().length());
		System.out.println("uri " + uri);
		
		Staff staff = session.get("staff");
		
		String error = "";
		if(staff == null) { // chưa đăng nhập
			error = "loginRequired";
		}else if(!staff.getAdmin() && uri.contains("/admin/")) { // không đúng vài trò
			System.out.println(staff.getAdmin());
			error = "accessDenied";
		}
		
		if(error.length() > 0) { // có lỗi
			session.set("security-uri", uri);
			resp.sendRedirect("/fteam/admin/login?error="+error);	
			return false;
		}
		return true;
	}
	
}
