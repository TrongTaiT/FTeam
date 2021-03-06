package com.fteam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fteam.model.Customer;
import com.fteam.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

	@Query("SELECT o FROM Order o WHERE o.customer = ?1")
	public List<Order> listAllByCustomer(Customer customer);

	@Query("UPDATE Order o SET o.status.id = 8 WHERE o.id=?1")
	@Modifying
	public void cancelOrder(Integer orderId);

}
