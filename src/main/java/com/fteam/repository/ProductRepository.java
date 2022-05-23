package com.fteam.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.fteam.model.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

}
