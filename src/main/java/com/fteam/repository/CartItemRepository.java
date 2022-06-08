package com.fteam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fteam.model.CartItem;
import com.fteam.model.Customer;
import com.fteam.model.Product;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	public List<CartItem> findByCustomer(Customer customer);
	
	public CartItem findByCustomerAndProduct(Customer customer, Product product);
	
}
