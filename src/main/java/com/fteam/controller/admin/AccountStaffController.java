package com.fteam.controller.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fteam.model.Staff;
import com.fteam.service.StaffService;
import com.fteam.utilities.SessionService;

@Controller
public class AccountStaffController {

	@Autowired
	StaffService staffService;

	@Autowired
	SessionService sessionService;

	@GetMapping("/admin/login")
	public String loginForm() {
		return "admin/login";
	}

	@PostMapping("/admin/login")
	public String loginAction( //
			Model model, //
			@RequestParam("email") String email, //
			@RequestParam("password") String password, //
			@RequestParam("error") Optional<String> errorMessage) {
		if (errorMessage.isPresent()) {
			String error = errorMessage.get();
			if (error.equals("accessDenied")) {
				error = "Không có quyền truy cập";
			} else if (error.equals("loginRequired")) {
				error = "Vui lòng đăng nhập";
			}
			model.addAttribute("message", error);
			System.out.println("Lỗi: " + error);
			return "admin/login";
		}

		try {
			Staff staff = staffService.getStaffByEmail(email);
			if (!staff.getPassword().equals(password)) {
				model.addAttribute("message", "Mật khẩu không hợp lệ");
			}
			// Đăng nhập thành công
			else {
				sessionService.set("staff", staff);
				String uri = sessionService.get("security-uri");
				if (uri != null) {
					return "redirect:" + uri;
				} else {
					model.addAttribute("message", "Đăng nhập thành công!");
					return "admin/index";
				}
			}
		} catch (Exception e) {
			model.addAttribute("message", "Email không hợp lệ");
		}
		return "admin/login";
	}
	
	@RequestMapping("/admin/logout")
	public String logout() {
		sessionService.remove("staff");
		return "redirect:/admin";
	}
	
}
