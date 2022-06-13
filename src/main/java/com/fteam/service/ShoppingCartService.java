package com.fteam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fteam.model.CartItem;
import com.fteam.model.Customer;
import com.fteam.model.Product;
import com.fteam.repository.CartItemRepository;
import com.fteam.repository.ProductRepository;

@Service
@Transactional
public class ShoppingCartService {
	
	@Autowired
	private CartItemRepository cartRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	public List<CartItem> listCartItems(Customer customer) {
		return cartRepo.findByCustomer(customer);
	}
	
	public Integer addProduct(Integer productId, Integer quantity, Customer customer) {
		Integer addedQuantity = quantity;
		
		Product product = productRepo.findById(productId).get();
		
		CartItem cartItem = cartRepo.findByCustomerAndProduct(customer, product);
		
		if (cartItem != null) {
			addedQuantity = cartItem.getQuantity() + quantity;
			cartItem.setQuantity(addedQuantity);
		} else {
			cartItem = new CartItem();
			cartItem.setQuantity(quantity);
			cartItem.setCustomer(customer);
			cartItem.setProduct(product);
		}
		
		cartRepo.save(cartItem);
		
		return quantity;
	}
	
	public float updateQuantity(Integer productId, Integer quantity, Customer customer) {
		cartRepo.updateQuantity(quantity, productId, customer.getId());		
		Product product = productRepo.findById(productId).get();
		
		float subtotal = product.getRealPrice() * quantity;
		return subtotal;
	}
	
	public void removeProduct(Integer productId, Customer customer) {
		cartRepo.deleteByCustomerAndProduct(customer.getId(), productId);
	}
	
	public void remove(CartItem cartItem) {
		cartRepo.delete(cartItem);
	}

}
