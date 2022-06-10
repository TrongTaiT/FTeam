package com.fteam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fteam.dto.CustomerDTO;
import com.fteam.model.Customer;
import com.fteam.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repo;

	@Autowired
	private ModelMapper mapper;

	public Customer save(Customer customer) {
		return repo.save(customer);
	}

	public Customer getAccount(String email, String password) {
		return repo.getByEmailAndPassword(email, password);
	}

	public boolean isEmailUnique(Integer id, String email) {
		Customer customerByEmail = repo.getCustomerByEmail(email);

		if (customerByEmail == null)
			return true;

		boolean isCreatingNew = (id == null);
		if (isCreatingNew) {
			if (customerByEmail != null)
				return false;
		} else {
			if (customerByEmail.getId() != id)
				return false;
		}

		return true;
	}

	public boolean isPhoneNumberUnique(Integer id, String phoneNumber) {
		Customer customer = repo.getCustomerByPhoneNumber(phoneNumber);

		if (customer == null)
			return true;

		boolean isCreatingNew = (id == null);
		if (isCreatingNew) {
			if (customer != null)
				return false;
		} else {
			if (customer.getId() != id)
				return false;
		}

		return true;
	}

	public Customer convertToEntity(CustomerDTO customerDTO) {
		return mapper.map(customerDTO, Customer.class);
	}

	public CustomerDTO convertToDTO(Customer customer) {
		return mapper.map(customer, CustomerDTO.class);
	}

}
