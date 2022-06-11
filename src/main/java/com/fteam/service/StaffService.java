package com.fteam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fteam.model.Product;
import com.fteam.model.Staff;
import com.fteam.repository.StaffRepository;

@Service
@Transactional
public class StaffService {
	
	public static final int STAFFS_PER_MANAGEMENT_PAGE = 10;

	@Autowired
	private StaffRepository repo;
	
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
	
	public void updateEnabled(Integer id, Boolean enabled) {
		repo.updateEnabled(id, enabled);
	}
	
	public  Page<Staff> listByPageable(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

		Pageable pageable = PageRequest.of(pageNum - 1, STAFFS_PER_MANAGEMENT_PAGE, sort);

		if (keyword != null) {
			return repo.findByKeywords(keyword, pageable);
		}

		return repo.findAll(pageable);
	}
	
}
