package com.fteam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fteam.model.Customer;
import com.fteam.model.Order;
import com.fteam.repository.OrderRepository;

@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;

	public List<Order> listAllByCustomer(Customer customer) {
		return orderRepo.listAllByCustomer(customer);
	}
	
	public Order getById(Integer id) {
		return orderRepo.getById(id);
	}

	public Order save(Order order) {
		return orderRepo.save(order);
	}

	public void cancelOrder(Integer orderId) {
		orderRepo.cancelOrder(orderId);
	}

}
