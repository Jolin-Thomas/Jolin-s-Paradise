package com.example.major.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.major.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
