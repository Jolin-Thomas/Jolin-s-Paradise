package com.example.major.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.example.major.dto.ProductDTO;
import com.example.major.model.Category;
import com.example.major.model.Product;
import com.example.major.service.CategoryService;
import com.example.major.service.ProductService;

@Controller
public class AdminController {
	
	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
 
	@Autowired
	CategoryService catservice;
	
	@Autowired
	ProductService proservice;
	
	//admin 
	@GetMapping("/admin")
	public String adminHome() {
		return "adminHome";
	}
	
	//----------------------------------------------------------------------------------------------------------------------//
	//------------------------------------------------category section------------------------------------------------------//
	//----------------------------------------------------------------------------------------------------------------------//
	
	//admin->categories 
	@GetMapping("/admin/categories")
	public String adminCategory(Model model) {
		model.addAttribute("categories", catservice.getAllCategory());
		return "categories";
	}
	
	//admin->categories->add
	@GetMapping("/admin/categories/add")
	public String getAdminCategoryAdd(Model model) {
		model.addAttribute("category", new Category());
		return "categoriesAdd";
	}
	
	//admin->categories->add->categories
	@PostMapping("/admin/categories/add")
	public String postAdminCategoryAdd(@ModelAttribute("category") Category category) {
		catservice.addCategory(category);
		return "redirect:/admin/categories";
	}
	
	//admin->categories->update
	@GetMapping("/admin/categories/update/{id}")
	public String updateCategory(@PathVariable int id, Model model) {
		Optional<Category> category = catservice.getCategoryById(id);
		if(category.isPresent()) {
			model.addAttribute("category", category.get());
			return "categoriesAdd";
		}
		else {
			return "404";
		}
	}
	
	//admin->categories->delete->categories
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCategory(@PathVariable int id) {
		catservice.removeCategoryById(id);
		return "redirect:/admin/categories";
	}
	
	//----------------------------------------------------------------------------------------------------------------------//
	//------------------------------------------------product section-------------------------------------------------------//
	//----------------------------------------------------------------------------------------------------------------------//
	
	//admin->products
	@GetMapping("/admin/products")
	public String adminProduct(Model model) {
		model.addAttribute("products", proservice.getAllProduct());
		return "products";
	}
	
	//admin->products->add
	@GetMapping("/admin/products/add")
	public String getAdminProductAdd(Model model) {
		model.addAttribute("productDTO",  new ProductDTO());
		model.addAttribute("categories", catservice.getAllCategory());
		return "productsAdd";
	}
	
	//admin->products->add->products
	@PostMapping("/admin/products/add")
	public String postAdminProductAdd(@ModelAttribute("productDTO")ProductDTO productDTO, 
									  @RequestParam("productImage")MultipartFile file, 
									  @RequestParam("imgName")String imgName) throws IOException {
		String imageUUID;
		Product product = new Product();
		
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(catservice.getCategoryById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		
		if(!file.isEmpty()) {
			imageUUID = file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		}
		else {
			imageUUID = imgName;
		}
		product.setImageName(imageUUID);
		proservice.addProduct(product);
		return "redirect:/admin/products";
	}
	
	//admin->products->update
	@GetMapping("/admin/products/update/{id}")
	public String updateProduct(@PathVariable Long id, Model model) {
		Product product = proservice.getProductById(id).get();
		ProductDTO productDTO = new ProductDTO();
		
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setCategoryId(product.getCategory().getId());
		productDTO.setPrice(product.getPrice());
		productDTO.setWeight(product.getWeight());
		productDTO.setDescription(product.getDescription());
		productDTO.setImageName(product.getImageName());
		
		model.addAttribute("categories", catservice.getAllCategory());
		model.addAttribute("productDTO", productDTO);
		return "productsAdd";
	}

	//admin->products->delete->products
	@GetMapping("/admin/products/delete/{id}")
	public String deleteProduct(@PathVariable Long id) {
		proservice.removeProductById(id);
		return "redirect:/admin/products";
	}
	
	
//	@GetMapping("/shop")
//	public String shop() {
//		return "shop";
//	} 
//	
//	@GetMapping("/login")
//	public String login() {
//		return "login";
//	}
//	
//	@GetMapping("/logout")
//	public String logout() {
//		return "adminHome";
//	}
//	
//	@GetMapping("/cart")
//	public String cart() {
//		return "cart";
//	}
//	
//	@GetMapping("/admin/products")
//	public String adminProductPage() {
//		return "products";
//	}
//	@PutMapping("/admin/products/add")
//	public String adminProductAddPage() {
//		return "productsAdd";
//	}
//	
//	
//	@GetMapping("/checkout")
//	public String checkout() {
//		return "checkout";
//	}
	
}
