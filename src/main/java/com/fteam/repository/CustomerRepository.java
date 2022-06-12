package com.fteam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fteam.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	@Query("SELECT c FROM Customer c WHERE c.email = :email")
	public Customer getCustomerByEmail(@Param("email") String email);

	@Query("SELECT c FROM Customer c WHERE c.email = :email AND c.password = :password")
	public Customer getByEmailAndPassword(@Param("email") String email, @Param("password") String password);

	@Query("UPDATE Customer c SET c.enabled = true WHERE c.id = ?1")
	@Modifying
	public void updateEnabled(Integer id);
	
	@Query("SELECT c FROM Customer c WHERE c.verificationCode = ?1")
	public Customer findByVerificationCode(String code);
	
}
