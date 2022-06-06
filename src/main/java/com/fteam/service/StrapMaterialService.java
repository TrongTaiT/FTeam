package com.fteam.service;

import java.util.List;

import com.fteam.model.StrapMaterial;
import com.fteam.repository.StrapMaterialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StrapMaterialService {

	@Autowired
	private StrapMaterialRepository repo;
	
	public List<StrapMaterial> listAll(){
		return repo.findAll();
	}
	
}
