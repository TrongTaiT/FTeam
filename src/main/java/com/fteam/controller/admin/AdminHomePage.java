package com.fteam.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminHomePage {

	@GetMapping("admin")
	public String viewAdminHomePage() {
		return "admin/index";
	}
	
}
