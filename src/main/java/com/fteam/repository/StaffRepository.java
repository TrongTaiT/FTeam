package com.fteam.repository;

import javax.transaction.Transactional;

import com.fteam.model.Product;
import com.fteam.model.Staff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface StaffRepository extends JpaRepository<Staff, Integer>{

	@Query("SELECT s FROM Staff s WHERE s.email = ?1")
	public Staff getStaffByEmail(String email);
	
	@Query("UPDATE Staff s SET s.fullname = ?2, s.email=?3, s.phoneNumber=?4, s.photo=?5 WHERE s.id=?1 ")
	@Modifying
	@Transactional
	public void updateAccount(Integer id, String fullname, String email, String phoneNumber, String photo);
	
}
