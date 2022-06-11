package com.fteam.repository;

import com.fteam.model.Staff;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface StaffRepository extends JpaRepository<Staff, Integer> {

	@Query("SELECT s FROM Staff s WHERE s.email = ?1")
	public Staff getStaffByEmail(String email);
	
	@Query("SELECT s FROM Staff s WHERE CONCAT(s.id, ' ', s.fullname, ' ', s.email) LIKE %?1%")
	Page<Staff> findByKeywords(String keyword, Pageable pageable);
	
	@Query("UPDATE Staff s SET s.enabled = ?2 WHERE s.id = ?1")
	@Modifying
	@Transactional
	public void updateEnabled(Integer id, Boolean enabled);
	
}
