package com.fteam.controller.admin;

import java.io.IOException;
import java.util.Optional;

import com.fteam.model.Staff;
import com.fteam.service.StaffService;
import com.fteam.utilities.FileUploadUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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
@RequestMapping("/admin/staff")
public class StaffManagementController {

	@Autowired
	private StaffService service;

	@RequestMapping("/page/{pageNum}")
	public String listByPage( //
			@PathVariable(name = "pageNum") int pageNum, //
			Model model, //
			@Param("sortField") String sortField, //
			@Param("sortDir") String sortDir, //
			@RequestParam(name = "keyword") Optional<String> keyword //
	) 
	{
		Page<Staff> page = service.listByPageable(pageNum, sortField, sortDir, keyword.orElse(""));
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc"));
		model.addAttribute("keyword", keyword.orElse(""));
		model.addAttribute("page", page);
		
		return "admin/staff/_table";
	}
	
	@GetMapping("")
	public String listFirstPage(Model model) {
		return listByPage(1, model, "id", "asc", Optional.empty());
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		Staff staff = new Staff();
		model.addAttribute("staff", staff);
		
		return "admin/staff/_form";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(Model model, //
			@PathVariable("id") Integer id //
	)
	{
		try {
			Staff staff = service.getStaffById(id);
			model.addAttribute("staff", staff);
		} catch (Exception e) {
			model.addAttribute("staff", new Staff());
			model.addAttribute("message", e.getMessage());
		}
		
		return "admin/staff/_form";
	}
	
	@GetMapping("/edit/{id}/enable/{status}")
	public String toggleEnabled( //
			@PathVariable("id") Integer id, //
			@PathVariable("status") Boolean status, //
			RedirectAttributes ra //
	)
	{
		service.updateEnabled(id, status);
		ra.addFlashAttribute("message", "Cập nhật thành công!");
		return "redirect:/admin/staff";
	}
	
	@PostMapping("/save")
	public String save( //
			RedirectAttributes ra, //
			Staff staff, //
			@RequestParam("image") MultipartFile multipartFile, //
			Model model //
	) throws IOException
	{
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			staff.setPhoto(fileName);
			Staff savedStaff = service.save(staff);
			
			String uploadDir = "images/staffs/" + savedStaff.getId();
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			// Tạo mới
			if(staff.getId() == null) {
				ra.addFlashAttribute("message", "Chưa chọn hình sản phẩm");
				return "admin/product/_form";
			} else {
				try {
					Staff staffInDB = service.getStaffById(staff.getId());
					staff.setPhoto(staffInDB.getPhoto());
					
					if (staff.getPassword().isBlank()) {
						staff.setPassword(staffInDB.getPassword());
					}
					if (staff.getAdmin() == null) {
						staff.setAdmin(staffInDB.getAdmin());
					}
					service.save(staff);
				} catch (Exception e) {
					ra.addFlashAttribute("message", e.getMessage());
					return "redirect:/admin/staff";
				}
			}
		}
		
		ra.addFlashAttribute("message", "Lưu nhân viên thành công!");
		
		return "redirect:/admin/staff/edit/" + staff.getId();
	}

}
