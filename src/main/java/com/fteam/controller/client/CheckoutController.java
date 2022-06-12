package com.fteam.controller.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fteam.model.CartItem;
import com.fteam.model.Customer;
import com.fteam.service.CheckoutService;
import com.fteam.service.CustomerService;
import com.fteam.service.ShoppingCartService;
import com.fteam.utilities.SessionService;

@Controller
public class CheckoutController {

	@Autowired
	private CheckoutService checkoutService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ShoppingCartService cartService;

	@Autowired
	private SessionService sessionService;

	@GetMapping("/cart/check-out")
	public String checkout( //
			RedirectAttributes ra, //
			Model model) //
	{
		Customer customer = sessionService.get("customer");
		
		if (customer == null) {
			ra.addFlashAttribute("message", "Vui lòng đăng nhập");
			return "redirect:/account/signin";
		}
		
		List<CartItem> cartItems = cartService.listCartItems(customer);
		
		
		return "";
	}

}
