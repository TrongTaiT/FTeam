package com.fteam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
	
	@GetMapping("/client/error/common-error")
	public void renderErrorPage() {
	}

}
