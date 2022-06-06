package com.fteam.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fteam.exception.ProductNotFoundException;
import com.fteam.model.Category;
import com.fteam.model.Product;
import com.fteam.repository.CategoryRepository;
import com.fteam.repository.ProductRepository;

@Service
public class ProductService {
	
	public static final int PRODUCTS_PER_PAGE = 3;

	@Autowired
	private ProductRepository productRepo;

	public List<Product> listAll() {
		return (List<Product>) productRepo.findAll();
	}

	public List<Product> listTop8ByDOM() {
		return productRepo.listTop8ByDateOfManufacture();
	}

	public List<Product> listTop8ByRandom() {
		return productRepo.listTop8ByRandom();
	}
	
	public Product getProduct(Integer id) throws ProductNotFoundException {
		try {
			return productRepo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ProductNotFoundException("Không tìm thấy sản phẩm có Id: " + id);
		}
	}
	
	public Page<Product> listByCategory(int pageNum, Integer categoryId) {
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
		
		return productRepo.findAllByCategoryId(categoryId, pageable);
	}

}
