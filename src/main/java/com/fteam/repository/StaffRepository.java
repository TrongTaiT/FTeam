package com.fteam.repository;

import com.fteam.model.Product;
import com.fteam.model.Staff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StaffRepository extends JpaRepository<Staff, Integer>{

	@Query("SELECT s FROM Staff s WHERE s.email = ?1")
	public Staff getStaffByEmail(String email);
	
}
