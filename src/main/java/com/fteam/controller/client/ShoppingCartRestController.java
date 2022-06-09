package com.fteam.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fteam.model.Customer;
import com.fteam.service.ShoppingCartService;
import com.fteam.utilities.FormatUtil;
import com.fteam.utilities.SessionService;

@RestController
public class ShoppingCartRestController {
	
	@Autowired
	private ShoppingCartService cartService;
	
	@Autowired
	private SessionService session;
	
	@PostMapping("/cart/add/{pid}/{qty}")
	public String addProductToCart( //
			@PathVariable("pid") Integer productId, //
			@PathVariable("qty") Integer quantity) //
	{
		Customer customer = session.get("customer");
		
		if (customer == null) {
			return "Bạn cần đăng nhập để mua hàng!";
		}
		
		Integer addedQuantity = cartService.addProduct(productId, quantity, customer);
		
		return addedQuantity + " sản phẩm đã được thêm vào giỏ hàng.";
	}
	
	@PostMapping("/cart/update/{pid}/{qty}")
	public String updateQuantity( //
			@PathVariable("pid") Integer productId, //
			@PathVariable("qty") Integer quantity) //
	{
		Customer customer = session.get("customer");
		
		if (customer == null) {
			return "Bạn cần đăng nhập để sử dụng giỏ hàng!";
		}
		
		float subtotal = cartService.updateQuantity(productId, quantity, customer);
		
		return FormatUtil.toStringNumber(subtotal);
	}
	
	@PostMapping("/cart/delete/{pid}")
	public String removeProductFromCart( //
			@PathVariable("pid") Integer productId)
	{
		Customer customer = session.get("customer");
		
		if (customer == null) {
			return "Bạn cần đăng nhập để sử dụng giỏ hàng!";
		}
		
		cartService.removeProduct(productId, customer);
		
		return "Đã xoá sản phẩm khỏi giỏ hàng";
	}

}
