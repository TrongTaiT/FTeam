package com.fteam.controller.admin;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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

import com.fteam.exception.ProductNotFoundException;
import com.fteam.model.Product;
import com.fteam.service.BrandService;
import com.fteam.service.CategoryService;
import com.fteam.service.FaceShapeService;
import com.fteam.service.ProductService;
import com.fteam.service.ShellMaterialService;
import com.fteam.service.StrapMaterialService;
import com.fteam.service.StyleService;
import com.fteam.service.WatchFaceService;
import com.fteam.utilities.FileUploadUtil;
import com.fteam.utilities.SessionService;

@Controller
@RequestMapping("/admin/product")
public class ProductManagementController {

	@Autowired
	private ProductService productService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private CategoryService categoryService;
	
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
	
	@Autowired
	HttpServletRequest req;
	
	@Autowired
	SessionService sessionService;

	@RequestMapping("/page/{pageNum}")
	public String listByPage( //
			@PathVariable(name = "pageNum") int pageNum, //
			Model model, //
			@Param("sortField") String sortField, //
			@Param("sortDir") String sortDir, //
			@RequestParam(name = "keyword") Optional<String> keyword) //
	{
		
		Page<Product> page = productService.listByPageable(pageNum, sortField, sortDir, keyword.orElse(""));
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("keyword", keyword.orElse(""));
		model.addAttribute("page", page);
		
		return "admin/product/_table";
	}
	
	@GetMapping("")
	public String listFirstPage(Model model) {
		return listByPage(1, model, "id", "asc", Optional.empty());
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
	
	@GetMapping("/edit/{id}/inStock/{status}")
	public String toggleInStock( //
			@PathVariable("id") Integer id, //
			@PathVariable("status") Boolean status, //
			RedirectAttributes ra)//
	{
		productService.updateInStock(id, status);
		ra.addFlashAttribute("message", "C???p nh???t th??nh c??ng!");
		return "redirect:/admin/product";
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

			String uploadDir = "images/products/" + savedProduct.getId();
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			// T???o m???i
			if (product.getId() == null) {
				ra.addFlashAttribute("message", "Ch??a ch???n h??nh s???n ph???m");
				return "admin/product/_form";				
			}
			// Update c??
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

		ra.addFlashAttribute("message", "L??u s???n ph???m th??nh c??ng!");

		return "redirect:/admin/product/edit/" + product.getId();

	}
	
	@RequestMapping("/delete/{id}")
	public String delete(RedirectAttributes ra, //
			@PathVariable("id") Product product)
	{
		productService.delete(product);
		
		String dir = "images/";
		
		String folder = "products";
		
		FileUploadUtil.deleteFile(req, dir, folder);
		
		ra.addFlashAttribute("message", "X??a s???n ph???m th??nh c??ng!");
		
		return "redirect:/admin/product";
	}

	private void getFormModelAttributes(Model model) {
		model.addAttribute("brands", brandService.listAll());
		model.addAttribute("categories", categoryService.listAll());
		model.addAttribute("styles", styleService.listAll());
		model.addAttribute("faceShapes", faceShapeService.listAll());
		model.addAttribute("shellMaterials", shellMaterialService.listAll());
		model.addAttribute("strapMaterials", strapMaterialService.listAll());
		model.addAttribute("watchFaces", watchFaceService.listAll());
	}

}
