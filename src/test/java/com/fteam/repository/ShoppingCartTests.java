package com.fteam.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.fteam.model.CartItem;
import com.fteam.model.Customer;
import com.fteam.model.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ShoppingCartTests {
	
	@Autowired
	private CartItemRepository cartRepo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testAddOneCartItem1() {
		Product product = entityManager.find(Product.class, 4);
		Customer customer = entityManager.find(Customer.class, 1);
		
		CartItem newItem = new CartItem();
		newItem.setCustomer(customer);
		newItem.setProduct(product);
		newItem.setQuantity(1);
		
		CartItem savedItem = cartRepo.save(newItem);
		
		assertTrue(savedItem.getId() > 0);
	}
	
	@Test
	public void testAddOneCartItem2() {
		Product product = entityManager.find(Product.class, 2);
		Customer customer = entityManager.find(Customer.class, 1);
		
		CartItem newItem = new CartItem();
		newItem.setCustomer(customer);
		newItem.setProduct(product);
		newItem.setQuantity(2);
		
		CartItem savedItem = cartRepo.save(newItem);
		
		assertTrue(savedItem.getId() > 0);
	}
	
	@Test
	public void testAddOneCartItem3() {
		Product product = entityManager.find(Product.class, 1);
		Customer customer = entityManager.find(Customer.class, 2);
		
		CartItem newItem = new CartItem();
		newItem.setCustomer(customer);
		newItem.setProduct(product);
		newItem.setQuantity(2);
		
		CartItem savedItem = cartRepo.save(newItem);
		
		assertTrue(savedItem.getId() > 0);
	}
	
	@Test
	public void testGetItemByCustomer() {
		Customer customer = new Customer();
		customer.setId(2);
		
		List<CartItem> result = cartRepo.findByCustomer(customer);
		
		assertTrue(result.size() == 1);
	}

}
