package com.fteam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fteam.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
