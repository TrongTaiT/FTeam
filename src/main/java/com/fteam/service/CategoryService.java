package com.fteam.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.fteam.exception.CategoryNotFoundException;
import com.fteam.model.Category;
import com.fteam.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repo;
	
	public List<Category> listAll(){
		return repo.findAll();
	}
	
	public Category getCategory(Integer id) throws CategoryNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new CategoryNotFoundException("Không tìm thấy danh mục có Id: " + id);
		}
	}

}
