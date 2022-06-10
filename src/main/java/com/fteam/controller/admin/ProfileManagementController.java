package com.fteam.controller.admin;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fteam.model.Staff;
import com.fteam.service.StaffService;
import com.fteam.utilities.FileUploadUtil;
import com.fteam.utilities.SessionService;

@Controller
@RequestMapping("/admin")
public class ProfileManagementController {
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private StaffService staffService;

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
	public String update( //
			@RequestParam("image") MultipartFile multipartFile, //
			Staff staff, //
			Model model
	) throws IOException
	{
		Staff staffInDB = staffService.getStaffById(staff.getId());
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			staff.setPhoto(fileName);
			
			String uploadDir = "images/staffs/" + staff.getId();
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			staff.setPhoto(staffInDB.getPhoto());
		}
		
		if (staff.getPassword() == null || staff.getPassword().isBlank()) {
			staff.setPassword(staffInDB.getPassword());
		}
		if (staff.getAdmin() == null) {
			staff.setAdmin(staffInDB.getAdmin());
		}
		if (staff.getEnabled() == null) {
			staff.setEnabled(staffInDB.getEnabled());
		}
		Staff savedStaff = staffService.save(staff);
		sessionService.set("staff", savedStaff);

		model.addAttribute("staff", savedStaff);
		return "admin/pages-profile";
	}
	
}
