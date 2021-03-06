package com.fteam.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fteam.exception.CategoryNotFoundException;
import com.fteam.exception.ProductNotFoundException;
import com.fteam.model.Category;
import com.fteam.model.Product;
import com.fteam.service.CategoryService;
import com.fteam.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping("product-details/{id}")
	public String showProductDetail( //
			@PathVariable("id") Integer id, //
			RedirectAttributes ra, //
			Model model //
	) {
		try {
			Product product = productService.getProduct(id);

			model.addAttribute("product", product);

			return "client/product-details";
		} catch (ProductNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
			return "redirect:/client/error/message";
		}
	}
	
	@GetMapping("/category/{category_id}/page/{pageNum}")
	public String showProductByCategoryByPage( //
			@PathVariable("category_id") Integer categoryId, //
			@PathVariable("pageNum") Integer pageNum, //
			RedirectAttributes ra, //
			Model model
	) {
		try {
			Category category = categoryService.getCategory(categoryId);
			
			Page<Product> page = productService.listByCategory(pageNum, categoryId);

			model.addAttribute("category", category);
			model.addAttribute("page", page);
			
			return "client/products";
		} catch (CategoryNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
			return "redirect:/client/error/message";			
		}
	}

	@GetMapping("/category/{category_id}")
	public String showProductByCategoryFirstPage( //
			@PathVariable("category_id") Integer categoryId, //
			RedirectAttributes ra, //
			Model model//
	) {
		return showProductByCategoryByPage(categoryId, 1, ra, model);
	}

}
