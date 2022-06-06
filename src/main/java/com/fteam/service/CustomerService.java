package com.fteam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fteam.dto.CustomerDTO;
import com.fteam.model.Customer;

@Service
public class CustomerService {

	@Autowired
	private ModelMapper mapper;

	public Customer convertToEntity(CustomerDTO customerDTO) {
		return mapper.map(customerDTO, Customer.class);
	}

	public CustomerDTO convertToDTO(Customer customer) {
		return mapper.map(customer, CustomerDTO.class);
	}

}
