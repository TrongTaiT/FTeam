package com.fteam.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fteam.service.CustomerService;

@RestController
public class AccountRestController {
	
	@Autowired
	private CustomerService service;
	
	@PostMapping("/customer/check_email")
	public String checkDuplicateEmail(Integer id, String email) {
		return service.isEmailUnique(id, email) ? "OK" : "Duplicated";
	}
	
	@PostMapping("/customer/check_phone_number")
	public String checkDuplicatePhoneNumber(Integer id, String phoneNumber) {
		return service.isPhoneNumberUnique(id, phoneNumber) ? "OK" : "Duplicated";
	}

}
