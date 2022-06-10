package com.fteam.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fteam.model.Staff;
import com.fteam.service.StaffService;

@Controller
@RequestMapping("/admin/staff")
public class StaffManagementController {

	@Autowired
	private StaffService service;

	@GetMapping("")
	public String viewStaffs(Model model) {
		List<Staff> staffs = service.listAll();

		model.addAttribute("staffs", staffs);

		return "admin/staffs";
	}

}
