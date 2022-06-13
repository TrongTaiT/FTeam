package com.fteam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fteam.model.Customer;
import com.fteam.model.Order;
import com.fteam.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;

	public List<Order> listAllByCustomer(Customer customer) {
		return orderRepo.listAllByCustomer(customer);
	}

	public Order save(Order order) {
		return orderRepo.save(order);
	}

}
