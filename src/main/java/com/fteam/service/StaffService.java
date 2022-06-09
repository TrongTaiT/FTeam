package com.fteam.service;

import java.util.List;

import com.fteam.model.Staff;
import com.fteam.repository.StaffRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StaffService {

	@Autowired
	StaffRepository repo;
	
	public Staff getStaffByEmail(String email) {
		return repo.getStaffByEmail(email);
	}
	
	public Staff getStaffById(Integer id) {
		return repo.getById(id);
	}
	
	public List<Staff> listAll(){
		return repo.findAll();
	}
	
	public Staff save(Staff staff) {
		return repo.save(staff);
	}
	
	public void updateAccount(Integer id, String fullname, String email, String phoneNumber, String photo) {
		repo.updateAccount(id, fullname, email, phoneNumber, photo);
	}
	
}
