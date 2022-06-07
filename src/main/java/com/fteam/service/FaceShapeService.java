package com.fteam.service;

import java.util.List;

import com.fteam.model.FaceShape;
import com.fteam.repository.FaceShapeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaceShapeService {

	@Autowired
	private FaceShapeRepository repo;
	
	public List<FaceShape> listAll(){
		return repo.findAll();
	}
	
}
