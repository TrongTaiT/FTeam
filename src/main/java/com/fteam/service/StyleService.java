package com.fteam.service;

import java.util.List;

import com.fteam.model.Style;
import com.fteam.repository.StyleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StyleService {
	
	@Autowired
	private StyleRepository repo;
	
	public List<Style> listAll(){
		return repo.findAll();
	}

}
