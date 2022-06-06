package com.fteam.controller.admin;

import java.io.IOException;

import javax.servlet.ServletContext;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fteam.exception.ProductNotFoundException;
import com.fteam.model.Product;
import com.fteam.service.BrandService;
import com.fteam.service.FaceShapeService;
import com.fteam.service.ProductService;
import com.fteam.service.ShellMaterialService;
import com.fteam.service.StrapMaterialService;
import com.fteam.service.StyleService;
import com.fteam.service.WatchFaceService;
import com.fteam.utilities.FileUploadUtil;

@Controller
@RequestMapping("/admin/product")
public class ProductManagementController {

	@Autowired
	private ProductService productService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private StyleService styleService;

	@Autowired
	private FaceShapeService faceShapeService;

	@Autowired
	private ShellMaterialService shellMaterialService;

	@Autowired
	private StrapMaterialService strapMaterialService;

	@Autowired
	private WatchFaceService watchFaceService;

	@Autowired
	ServletContext servletContext;

	@GetMapping("")
	public String index(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("products", productService.listAll());

		return "admin/product/products";
	}

	@GetMapping("/form")
	public String form(Model model) {
		getFormModelAttributes(model);
		model.addAttribute("product", new Product());

		return "admin/product/_form";
	}

	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		getFormModelAttributes(model);

		try {
			Product product = productService.getProduct(id);
			model.addAttribute("product", product);
		} catch (ProductNotFoundException e) {
			model.addAttribute("product", new Product());
			model.addAttribute("message", e.getMessage());
		}

		return "admin/product/_form";
	}

	@PostMapping("/save")
	public String save( //
			RedirectAttributes ra, //
			Product product, //
			@RequestParam("photo") MultipartFile multipartFile, //
			Model model //
	) throws IOException //
	{
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			product.setMainImage(fileName);
			Product savedProduct = productService.save(product);

			String uploadDir = servletContext.getRealPath("/images/products/" //
					+ savedProduct.getId());
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			// Tạo mới
			if (product.getId() == null) {
				ra.addFlashAttribute("message", "Chưa chọn hình sản phẩm");
				return "admin/product/_form";				
			}
			// Update cũ
			else {
				try {
					Product productInDB = productService.getProduct(product.getId());
					product.setMainImage(productInDB.getMainImage());
					
					productService.save(product);
				} catch (ProductNotFoundException e) {
					ra.addAttribute("message", e.getMessage());
					return "redirect:/admin/product";
				}
			}
		}		

		ra.addFlashAttribute("message", "Lưu sản phẩm thành công!");

		return "redirect:/admin/product/edit/" + product.getId();

	}

	private void getFormModelAttributes(Model model) {
		model.addAttribute("brands", brandService.listAll());
		model.addAttribute("styles", styleService.listAll());
		model.addAttribute("faceShapes", faceShapeService.listAll());
		model.addAttribute("shellMaterials", shellMaterialService.listAll());
		model.addAttribute("strapMaterials", strapMaterialService.listAll());
		model.addAttribute("watchFaces", watchFaceService.listAll());
	}

}
