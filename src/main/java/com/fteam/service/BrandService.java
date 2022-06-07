package com.fteam.service;

import java.util.List;

import com.fteam.model.Brand;
import com.fteam.repository.BrandRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

	@Autowired
	private BrandRepository repo;
	
	public List<Brand> listAll(){
		return repo.findAll();
	}
	
}
