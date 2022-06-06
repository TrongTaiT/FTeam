package com.fteam.controller.client;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fteam.dto.CustomerDTO;
import com.fteam.service.CustomerService;

@Controller
public class AccountController {
	
	@Autowired
	private CustomerService service;
	
	@GetMapping("/account/signup")
	public String viewSignup(Model model) {
		
		model.addAttribute("customer", new CustomerDTO());
		
		return "client/signup";
	}
	
	@PostMapping("/account/signup")
	public String register( //
			Model model, //
			@Valid @ModelAttribute("customer") CustomerDTO customer, //
			BindingResult result //
	) {
		if (result.hasErrors()) {
			return "client/signup";
		}
		
		
		
		return "redirect:/";
	}
	
	@GetMapping("/account/signin")
	public String viewSignin() {		
		return "client/signin";
	}

}
