package com.example.major.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.major.model.Category;
import com.example.major.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository catrepo;
	
	public List<Category> getAllCategory() {
		return catrepo.findAll();
	}
	
	public void addCategory(Category category) {
		catrepo.save(category);
	}

	public void removeCategoryById(int id) {
		catrepo.deleteById(id);
	}
	
	public Optional<Category> getCategoryById(int id) {
		return catrepo.findById(id);
	}
	
}
