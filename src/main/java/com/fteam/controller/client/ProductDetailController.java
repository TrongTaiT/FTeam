package com.fteam.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fteam.exception.ProductNotFoundException;
import com.fteam.model.Product;
import com.fteam.service.ProductService;

@Controller
public class ProductDetailController {

	@Autowired
	private ProductService service;

	@GetMapping("product-details/{id}")
	public String showProductDetail( //
			@PathVariable("id") Integer id, //
			RedirectAttributes ra, //
			Model model //
	) {
		try {
			Product product = service.getProduct(id);
			
			model.addAttribute("product", product);

			return "client/product-details";
		} catch (ProductNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
			return "redirect:/client/error/message";
		}
	}

}
