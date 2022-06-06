package com.fteam.controller.client;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fteam.dto.CustomerDTO;
import com.fteam.model.Customer;
import com.fteam.service.CustomerService;
import com.fteam.utilities.CookieService;
import com.fteam.utilities.SessionService;

@Controller
public class AccountController {

	@Autowired
	private CustomerService service;

	@Autowired
	private SessionService session;
	
	@Autowired
	private CookieService cookieService;

	@GetMapping("/account/signup")
	public String viewSignup(Model model) {

		model.addAttribute("customer", new CustomerDTO());

		return "client/signup";
	}

	@PostMapping("/account/signup")
	public String signup( //
			Model model, //
			@Valid @ModelAttribute("customer") CustomerDTO customerDTO, //
			BindingResult result //
	) {
		if (result.hasErrors()) {
			return "client/signup";
		}

		Customer customer = service.convertToEntity(customerDTO);
		Customer savedCustomer = service.save(customer);

		session.set("customer", savedCustomer);

		return "redirect:/";
	}

	@GetMapping("/account/signin")
	public String viewSignin(Model model) //
	{
		String email = cookieService.getValue("email");
		if (!email.isBlank()) {
			model.addAttribute("email", email);
		}

		String password = cookieService.getValue("password");
		if (!password.isBlank()) {
			model.addAttribute("password", password);
		}
		
		return "client/signin";
	}

	@PostMapping("/account/signin")
	public String signin( //
			Model model, //
			@RequestParam("email") Optional<String> email, //
			@RequestParam("password") Optional<String> password, //
			@RequestParam("remember") Optional<Boolean> remember) //
	{
		Customer customer = service.getAccount(email.orElse(""), password.orElse(""));

		if (customer == null) {
			model.addAttribute("message", "Sai tên tài khoản hoặc mật khẩu!");
			return "client/signin";
		} else {
			session.set("customer", customer);
		}
		
		if (remember.orElse(false) == true) {
			cookieService.add("email", email.get(), 7);
			cookieService.add("password", password.get(), 7);
		} else {
			if (cookieService.get("email") != null) {
				cookieService.remove("email");
			}
			if (cookieService.get("password") != null) {
				cookieService.remove("password");
			}
		}

		return "redirect:/";
	}

	@PostMapping("/account/signout")
	public String signout() {
		session.remove("customer");
		return "redirect:/";
	}

}
