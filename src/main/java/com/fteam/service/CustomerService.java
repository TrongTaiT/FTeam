package com.fteam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.fteam.dto.CustomerDTO;
import com.fteam.model.Customer;
import com.fteam.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repo;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private JavaMailSender mailSender;

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

	public Customer convertToEntity(CustomerDTO customerDTO) {
		return mapper.map(customerDTO, Customer.class);
	}

	public CustomerDTO convertToDTO(Customer customer) {
		return mapper.map(customer, CustomerDTO.class);
	}
	
	public void sendVerificationEmail(Customer customer, String siteURL) throws UnsupportedEncodingException, MessagingException {
		String subject = "Please verify your registraton";
		String senderName = "FTeam";
		
		String mailContent = "<p>Dear " + customer.getFullname() + ",</p>";
		mailContent += "<p>Please click the link below to verify to your registration:</p>";
		
		String verifyURL = siteURL + "/verify?code=" + customer.getVerificationCode();
		mailContent += "<h3><a href=\"" + verifyURL + "\">VERIFY</a></h3>";
		
		mailContent += "<p>Thank you <br>The Fteam</p>";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom("fteam.net", senderName);
		helper.setTo(customer.getEmail());
		helper.setSubject(subject);
		helper.setText(mailContent, true);
		
		mailSender.send(message);
	}

}
