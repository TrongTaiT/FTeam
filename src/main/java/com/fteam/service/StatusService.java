package com.fteam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fteam.model.Status;
import com.fteam.repository.StatusRepository;

@Service
public class StatusService {

	@Autowired
	private StatusRepository statusRepo;

	public Status findById(Integer statusId) {
		return statusRepo.findById(statusId).get();
	}

}
