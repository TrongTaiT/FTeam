package com.fteam.controller.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fteam.model.Product;
import com.fteam.service.ProductService;

@Controller
public class ClientHomePage {

	@Autowired
	private ProductService service;

	@GetMapping("")
	public String viewClientHomePage() {
		return "client/index";
	}

	@ModelAttribute("newProducts")
	public List<Product> getNewProducts() {
		return service.listTop8ByDOM();
	}

	@ModelAttribute("randomProducts")
	public List<Product> getRandomProducts() {
		return service.listTop8ByRandom();
	}

}
