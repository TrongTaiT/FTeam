package com.fteam.repository;

import java.util.List;


import com.fteam.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	public Page<Product> findAllByProductNameLike(String keyword, Pageable pageable);

	@Query(value = "SELECT * FROM Products ORDER BY day_of_manufacture LIMIT 8", //
			nativeQuery = true)
	public List<Product> listTop8ByDateOfManufacture();
	
	@Query(value = "SELECT * FROM products ORDER BY RAND() LIMIT 8", //
			nativeQuery = true)
	public List<Product> listTop8ByRandom();
	
	@Query("SELECT p FROM Product p WHERE CONCAT(p.id, ' ', p.productName, ' ', p.brand.name) LIKE %?1%")
	Page<Product> findByKeywords(String keyword, Pageable pageable);
	
	public Page<Product> findAllByCategoryId(Integer categoryId, Pageable pageable);

	@Query("UPDATE Product p SET p.inStock = ?2 WHERE p.id = ?1")
	@Modifying
	@Transactional
	public void updateInStock(Integer id, Boolean inStock);

}
