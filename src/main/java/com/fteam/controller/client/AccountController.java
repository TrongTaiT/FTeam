package com.fteam.controller.client;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.bytebuddy.utility.RandomString;

import com.fteam.Utility;
import com.fteam.dto.CustomerDTO;
import com.fteam.model.Customer;
import com.fteam.service.CustomerService;
import com.fteam.utilities.CookieService;
import com.fteam.utilities.SessionService;

@Controller
public class AccountController {

	@Autowired
	private CustomerService service;

	@Autowired
	private SessionService session;

	@Autowired
	private CookieService cookieService;

	@GetMapping("/account/signup")
	public String viewSignup(Model model) {

		model.addAttribute("customer", new CustomerDTO());

		return "client/signup";
	}
	
	@GetMapping("/account/create-customer")
	public String verification() {
		return "client/signup_result/verificationAccount";
	}
	
	@GetMapping("/account/verify")
	public String verifyAccount(@Param("code") String code, Model model) {
		boolean verified = service.verify(code);
		
		String pageTitle = verified ? "Verification Successded" : "Verification Failed";
		model.addAttribute("pageTitle", pageTitle);
		return "client/signup_result/" + (verified ? "verify_success" : "verify_fail");
	}

	@PostMapping("/account/signup")
	public String signup( //
			Model model, //
			@Valid @ModelAttribute("customer") CustomerDTO customerDTO, //
			BindingResult result, //
			HttpServletRequest req //
	) throws UnsupportedEncodingException, MessagingException {
		if (result.hasErrors()) {
			return "client/signup";
		}

		Customer customer = service.convertToEntity(customerDTO);
		customer.setEnabled(false);
		
		String randomCode = RandomString.make(64);
		customer.setVerificationCode(randomCode);
		
		
		Customer savedCustomer = service.save(customer);
		
		String siteURL = Utility.getSiteURL(req);
		service.sendVerificationEmail(savedCustomer, siteURL);

//		session.set("customer", savedCustomer);

		return "redirect:/account/create-customer";
	}

	@GetMapping("/account/signin")
	public String viewSignin(Model model) //
	{
		String email = cookieService.getValue("email");
		if (!email.isBlank()) {
			model.addAttribute("email", email);
		}

		String password = cookieService.getValue("password");
		if (!password.isBlank()) {
			model.addAttribute("password", password);
		}

		return "client/signin";
	}

	@PostMapping("/account/signin")
	public String signin( //
			Model model, //
			@RequestParam("email") Optional<String> email, //
			@RequestParam("password") Optional<String> password, //
			@RequestParam("remember") Optional<Boolean> remember) //
	{
		Customer customer = service.getAccount(email.orElse(""), password.orElse(""));

		if (customer == null) {
			model.addAttribute("message", "Sai tên tài khoản hoặc mật khẩu!");
			return "client/signin";
		} else {
			session.set("customer", customer);
		}

		if (remember.orElse(false) == true) {
			cookieService.add("email", email.get(), 7);
			cookieService.add("password", password.get(), 7);
		} else {
			if (cookieService.get("email") != null) {
				cookieService.remove("email");
			}
			if (cookieService.get("password") != null) {
				cookieService.remove("password");
			}
		}

		return "redirect:/";
	}

	@PostMapping("/account/signout")
	public String signout() {
		session.remove("customer");
		return "redirect:/";
	}

	@GetMapping("/account/edit")
	public String viewAccount( //
			RedirectAttributes ra, //
			Model model) //
	{
		Customer customer = session.get("customer");

		if (customer == null) {
			ra.addFlashAttribute("message", "Bạn chưa đăng nhập");
			return "redirect:/account/signin";
		}

		model.addAttribute("customer", customer);

		return "client/account";
	}

	@PostMapping("/account/update")
	public String viewAccount( //
			RedirectAttributes ra, //
			Model model, //
			@ModelAttribute("customer") Customer customer, //
			@RequestParam("oldPassword") Optional<String> oldPassword, //
			@RequestParam("newPassword") Optional<String> newPassword, //
			@RequestParam("confirmNewPassword") Optional<String> confirmNewPassword) //
	{
		System.out.println(customer);
		Customer customerInDB = null;

		try {
			// change password
			if (oldPassword.isPresent() && !oldPassword.get().isBlank() //
					&& newPassword.isPresent() && !newPassword.get().isBlank() //
					&& confirmNewPassword.isPresent() && !confirmNewPassword.get().isBlank()) {
				customerInDB = service.getAccount(customer.getEmail(), oldPassword.get());

				// old password wrong
				if (customerInDB == null) {
					model.addAttribute("message", "Sai mật khẩu cũ");
					return "client/account";
				}
				// check equality of newPassword and confirmNewPassword
				else {
					if (newPassword.get().equals(confirmNewPassword.get())) {
						customer.setPassword(newPassword.get());
					} else {
						model.addAttribute("message", "Mật khẩu mới không trùng");
						return "client/account";
					}
				}
			}
			// not change password
			else {
				customerInDB = service.findByEmail(customer.getEmail());
				customer.setPassword(customerInDB.getPassword());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		customer.setEnabled(true);
		Customer savedCustomer = service.save(customer);

		session.set("customer", savedCustomer);

		ra.addFlashAttribute("message", "Cập nhật tài khoản thành công!");

		return "redirect:/account/edit";
	}

}
