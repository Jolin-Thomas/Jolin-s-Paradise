package com.example.major.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.major.service.CategoryService;
import com.example.major.service.ProductService;

@Controller
public class HomeController {
	
	@Autowired
	CategoryService catservice;
	
	@Autowired
	ProductService proservice;
	
	@GetMapping({"/", "/home"})
	public String home(Model model) {
		return "index";	
	}
	
	@GetMapping("/shop")
	public String shop(Model model) {
		model.addAttribute("categories", catservice.getAllCategory());
		model.addAttribute("products", proservice.getAllProduct());
		return "shop";	
	}
	
	@GetMapping("/shop/category/{id}")
	public String shopByCategory(Model model, @PathVariable int id) {
		model.addAttribute("categories", catservice.getAllCategory());
		model.addAttribute("products", proservice.getAllProductByCategoryId(id));
		return "shop";	
	}
	
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(Model model, @PathVariable Long id) {
		model.addAttribute("product", proservice.getProductById(id).get());
		return "viewProduct";	
	}
	
	
		

}
