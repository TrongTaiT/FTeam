package com.fteam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fteam.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
