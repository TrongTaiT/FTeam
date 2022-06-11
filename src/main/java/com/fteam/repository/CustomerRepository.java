package com.fteam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fteam.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	@Query("SELECT c FROM Customer c WHERE c.email = :email")
	public Customer getCustomerByEmail(@Param("email") String email);

	@Query("SELECT c FROM Customer c WHERE c.email = :email AND c.password = :password")
	public Customer getByEmailAndPassword(@Param("email") String email, @Param("password") String password);

	
	
}
