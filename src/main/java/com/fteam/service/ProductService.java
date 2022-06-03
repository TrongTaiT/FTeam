package com.fteam.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fteam.exception.ProductNotFoundException;
import com.fteam.model.Product;
import com.fteam.repository.ProductRepository;

@Service
public class ProductService {

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

}
