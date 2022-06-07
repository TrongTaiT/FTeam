package com.fteam.service;

import java.util.List;

import com.fteam.model.ShellMaterial;
import com.fteam.repository.ShellMaterialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShellMaterialService {

	@Autowired
	private ShellMaterialRepository repo;
	
	public List<ShellMaterial> listAll(){
		return repo.findAll();
	}
	
}
