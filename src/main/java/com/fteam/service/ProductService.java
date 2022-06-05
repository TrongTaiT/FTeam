package com.fteam.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fteam.exception.ProductNotFoundException;
import com.fteam.model.Product;
import com.fteam.repository.ProductRepository;

@Service
public class ProductService {
	
	public static final int PRODUCTS_PER_PAGE = 3;

	@Autowired
	private ProductRepository repo;

	public List<Product> listAll() {
		return (List<Product>) repo.findAll();
	}

	public List<Product> listTop8ByDOM() {
		return repo.listTop8ByDateOfManufacture();
	}

	public List<Product> listTop8ByRandom() {
		return repo.listTop8ByRandom();
	}
	
	public Product getProduct(Integer id) throws ProductNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ProductNotFoundException("Không tìm thấy sản phẩm có Id: " + id);
		}
	}
	
	public Page<Product> listByCategory(int pageNum, Integer categoryId) {
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
		
		return repo.findAllByCategoryId(categoryId, pageable);
	}

}
