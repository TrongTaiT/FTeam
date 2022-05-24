package com.fteam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fteam.model.Product;
import com.fteam.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repo;
	
	public List<Product> listAll() {
		return (List<Product>) repo.findAll();
	}
	
	public List<Product> list8NewProducts() {
		return null;
	}

}
