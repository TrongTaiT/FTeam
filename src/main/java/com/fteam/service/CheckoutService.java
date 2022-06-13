package com.fteam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fteam.model.CartItem;
import com.fteam.model.Customer;
import com.fteam.model.Order;
import com.fteam.model.OrderDetail;

@Service
public class CheckoutService {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private ShoppingCartService cartService;
	
	@Autowired
	private StatusService statusService;

	public void doCheckout(Customer customer) {
		Order savedOrder = saveOrder(customer);
		
		List<CartItem> cartItems = cartService.listCartItems(customer);
		
		createOrderDetailsAndRemoveCartItems(cartItems, savedOrder);		
	}

	private Order saveOrder(Customer customer) {
		Order order = new Order();
		order.setCustomer(customer);
		order.setAddress(customer.getAddress());
		order.setStatus(statusService.findById(3));
		
		return orderService.save(order);
	}

	private void createOrderDetailsAndRemoveCartItems(List<CartItem> cartItems, Order savedOrder) {		
		for (CartItem item : cartItems) {
			OrderDetail orderDetail = new OrderDetail();
			
			orderDetail.setPrice(item.getProduct().getRealPrice());
			orderDetail.setQuantity(item.getQuantity());
			orderDetail.setOrder(savedOrder);
			orderDetail.setProduct(item.getProduct());
			
			orderDetailService.save(orderDetail);
			cartService.remove(item);
		}
	}

}
