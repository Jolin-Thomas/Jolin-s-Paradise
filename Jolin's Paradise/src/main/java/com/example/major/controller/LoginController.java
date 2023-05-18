package com.example.major.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.example.major.model.Role;
import com.example.major.model.User;
import com.example.major.repository.RoleRepository;
import com.example.major.repository.UserRepository;

@Controller
public class LoginController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UserRepository userrepo;
	
	@Autowired
	RoleRepository rolerepo;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register") 
	public String registerGet() {
		return "register";
	}
	
	@PostMapping("/register")
	public String registerPost(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException {
		String password = user.getPassword();
		user.setPassword(bCryptPasswordEncoder.encode(password));
		List<Role> roles = new ArrayList<>();
		roles.add(rolerepo.findById(2).get());
		user.setRoles(roles);
		userrepo.save(user);
		request.login(user.getEmail(), password);
		return "redirect:/";
	}
	
}
