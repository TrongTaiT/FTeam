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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("dashboard")
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
	
	@GetMapping("product")
	public String index(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "admin/product-management";
	}
	
	@GetMapping("product/edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		try {
			Product product = productService.getProduct(id);
			model.addAttribute("product", product);
			
		} catch (ProductNotFoundException e) {
			model.addAttribute("product", new Product());
			model.addAttribute("message", e.getMessage());
		}

		return "admin/product-management";
	}
	
	@ModelAttribute("products")
	public List<Product> listAllProducts(){
		return productService.listAll();
	}
	
	@ModelAttribute("brands")
	public List<Brand> listAllBrands(){
		return brandService.listAll();
	}
	
	@ModelAttribute("styles")
	public List<Style> listAllStyle(){
		return styleService.listAll();
	}
	
	@ModelAttribute("faceShapes")
	public List<FaceShape> listAllFaceShapes(){
		return faceShapeService.listAll();
	}
	
	@ModelAttribute("shellMaterials")
	public List<ShellMaterial> listAllShellMaterials(){
		return shellMaterialService.listAll();
	}
	
	@ModelAttribute("strapMaterials")
	public List<StrapMaterial> listAllStrapMaterials(){
		return strapMaterialService.listAll();
	}
	
	@ModelAttribute("watchFaces")
	public List<WatchFace> listAllWatchFaces(){
		return watchFaceService.listAll();
	}
	
}
