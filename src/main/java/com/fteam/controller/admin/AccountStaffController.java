package com.fteam.controller.admin;

import java.util.Optional;

import com.fteam.model.Staff;
import com.fteam.repository.StaffRepository;
import com.fteam.utilities.SessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountStaffController {

	@Autowired
	StaffRepository repo;

	@Autowired
	SessionService session;

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
			String message = errorMessage.get();
			if (message.equals("accessDenied")) {
				message = "Không có quyền truy cập";
			} else if (message.equals("loginRequired")) {
				message = "Vui lòng đăng nhập";
			}
			model.addAttribute("message", message);
			System.out.println("Lỗi: " + message);
			return "admin/login";
		}

		try {
			Staff staff = repo.getStaffByEmail(email);
			if (!staff.getPassword().equals(password)) {
				model.addAttribute("message", "Mật khẩu không hợp lệ");
			}
			// Đăng nhập thành công
			else {
				session.set("staff", staff);
				String uri = session.get("security-uri");
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

}
