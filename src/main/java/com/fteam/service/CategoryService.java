package com.fteam.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fteam.exception.CategoryNotFoundException;
import com.fteam.model.Category;
import com.fteam.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repo;
	
	public Category getCategory(Integer id) throws CategoryNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new CategoryNotFoundException("Không tìm thấy danh mục có Id: " + id);
		}
	}

}
