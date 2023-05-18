package com.example.major.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.major.model.Product;
import com.example.major.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository prorepo;
	
	public List<Product> getAllProduct() {
		return prorepo.findAll();
	}
	
	public void addProduct(Product product) {
		prorepo.save(product);
	}
	
	public void removeProductById(Long id) {
		prorepo.deleteById(id);
	}
	
	public Optional<Product> getProductById(Long id) {
		return prorepo.findById(id);
	}
	
	public List<Product> getAllProductByCategoryId(int id) {
		return prorepo.findAllByCategory_Id(id);
	}
	
}
