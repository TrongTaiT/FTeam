package com.fteam.service;

import java.util.List;

import com.fteam.model.WatchFace;
import com.fteam.repository.WatchFaceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchFaceService {

	@Autowired
	private WatchFaceRepository repo;
	
	public List<WatchFace> listAll(){
		return repo.findAll();
	}
	
}
