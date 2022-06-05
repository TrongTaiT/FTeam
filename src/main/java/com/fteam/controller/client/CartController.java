package com.fteam.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
	
	@GetMapping("/cart")
	public String viewCart() {
		return "client/cart";
	}

}
