package com.fteam.controller.admin;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fteam.exception.ProductNotFoundException;
import com.fteam.model.Brand;
import com.fteam.model.Category;
import com.fteam.model.FaceShape;
import com.fteam.model.Product;
import com.fteam.model.ShellMaterial;
import com.fteam.model.StrapMaterial;
import com.fteam.model.Style;
import com.fteam.model.WatchFace;
import com.fteam.repository.BrandRepository;
import com.fteam.repository.CategoryRepository;
import com.fteam.repository.FaceShapeRepository;
import com.fteam.repository.ProductRepository;
import com.fteam.repository.StyleRepository;
import com.fteam.service.BrandService;
import com.fteam.service.CategoryService;
import com.fteam.service.FaceShapeService;
import com.fteam.service.ProductService;
import com.fteam.service.ShellMaterialService;
import com.fteam.service.StrapMaterialService;
import com.fteam.service.StyleService;
import com.fteam.service.WatchFaceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("dashboard")
public class ProductManagementController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
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
	
	@GetMapping("product")
	public String index(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		
		List<Brand> brands = brandService.listAll();
		model.addAttribute("brands", brands);
		
		List<Category> categories = categoryService.listAll();
		model.addAttribute("categories", categories);
		
		List<Style> styles = styleService.listAll();
		model.addAttribute("styles", styles);
		
		List<FaceShape> faceShapes = faceShapeService.listAll();
		model.addAttribute("faceShapes", faceShapes);
		
		List<ShellMaterial> shellMaterials = shellMaterialService.listAll();
		model.addAttribute("shellMaterials", shellMaterials);
		
		List<StrapMaterial> strapMaterials = strapMaterialService.listAll();
		model.addAttribute("strapMaterials", strapMaterials);
		
		List<WatchFace> watchFaces = watchFaceService.listAll();
		model.addAttribute("watchFaces", watchFaces);
		
		
		List<Product> list = productService.listAll();
		model.addAttribute("products", list);
		return "admin/product-management";
	}
	
	@GetMapping("product/edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) throws ProductNotFoundException {
		Product product = productService.getProduct(id);
		model.addAttribute("product", product);
		
		List<Brand> brands = brandService.listAll();
		model.addAttribute("brands", brands);
		
		List<Category> categories = categoryService.listAll();
		model.addAttribute("categories", categories);
		
		List<Style> styles = styleService.listAll();
		model.addAttribute("styles", styles);
		
		List<FaceShape> faceShapes = faceShapeService.listAll();
		model.addAttribute("faceShapes", faceShapes);
		
		List<ShellMaterial> shellMaterials = shellMaterialService.listAll();
		model.addAttribute("shellMaterials", shellMaterials);
		
		List<StrapMaterial> strapMaterials = strapMaterialService.listAll();
		model.addAttribute("strapMaterials", strapMaterials);
		
		List<WatchFace> watchFaces = watchFaceService.listAll();
		model.addAttribute("watchFaces", watchFaces);
		
		List<Product> products = productService.listAll();
		model.addAttribute("products", products);
		
		return "admin/product-management";
		
	}
	
}
