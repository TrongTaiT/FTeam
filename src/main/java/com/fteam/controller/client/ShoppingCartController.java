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
import com.fteam.service.ShoppingCartService;
import com.fteam.utilities.SessionService;

@Controller
public class ShoppingCartController {
	
	@Autowired
	private ShoppingCartService cartService;
	
	@Autowired
	private CheckoutService checkoutService;
	
	@Autowired
	private SessionService session;
	
	@GetMapping("/cart")
	public String showShoppingCart( //
			Model model) //
	{
		Customer customer = session.get("customer");
		List<CartItem> cartItems = cartService.listCartItems(customer);
		
		model.addAttribute("cartItems", cartItems);
		
		return "client/cart";
	}
	
	@GetMapping("/cart/check-out")
	public String checkout( //
			RedirectAttributes ra) //
	{
		Customer customer = session.get("customer");

		if (customer == null) {
			ra.addFlashAttribute("message", "Bạn chưa đăng nhập");
			return "redirect:/account/signin";
		}
		
		try {
			checkoutService.doCheckout(customer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/account/order";
	}

}
