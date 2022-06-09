package com.fteam.controller.admin;

import java.io.IOException;

import com.fteam.model.Product;
import com.fteam.model.Staff;
import com.fteam.service.ProductService;
import com.fteam.service.StaffService;
import com.fteam.utilities.FileUploadUtil;
import com.fteam.utilities.SessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class ProfileManagementController {
	
	@Autowired
	SessionService sessionService;
	
	@Autowired
	StaffService staffService;

	@GetMapping("/my-account/{id}")
	public String getMyAccount(//
			Model model, //
			@PathVariable("id") Integer id//
	) 
	{
		Staff staff = sessionService.get("staff");
		model.addAttribute("staff", staff);
		
		return "admin/pages-profile";
	}
	
	@PostMapping("/my-account/save")
	public String update(//
			RedirectAttributes ra, //
			@RequestParam("image") MultipartFile multipartFile, //
			Staff staff, //
			Model model
	) throws IOException
	{
		
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			staff.setPhoto(fileName);
			
			String uploadDir = "images/staffs/" + staff.getId();
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			Staff staffInDB = staffService.getStaffById(staff.getId());
			staff.setPhoto(staffInDB.getPhoto());
		}
		
		staffService.updateAccount(staff.getId(), staff.getFullname(), staff.getEmail(), staff.getPhoneNumber(), staff.getPhoto());
		Staff staffLoadAgain = staffService.getStaffById(staff.getId());
		sessionService.set("staff", staffLoadAgain);
		System.out.println(staff.getId() + staff.getFullname() + staff.getEmail() + staff.getPhoneNumber() + staff.getPhoto());
		ra.addFlashAttribute("message", "Lưu sản phẩm thành công!");
		return "redirect:/admin/my-account/" + staff.getId();
	}
	
}
