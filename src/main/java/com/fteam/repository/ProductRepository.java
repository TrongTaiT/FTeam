package com.fteam.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fteam.model.Category;
import com.fteam.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	public Page<Product> findAllByProductNameLike(String keyword, Pageable pageable);

	@Query(value = "SELECT * FROM Products ORDER BY day_of_manufacture LIMIT 8", //
			nativeQuery = true)
	public List<Product> listTop8ByDateOfManufacture();
	
	@Query(value = "SELECT * FROM products ORDER BY RAND() LIMIT 8", //
			nativeQuery = true)
	public List<Product> listTop8ByRandom();
	
	public Page<Product> findAllByCategoryId(Integer categoryId, Pageable pageable);

}
