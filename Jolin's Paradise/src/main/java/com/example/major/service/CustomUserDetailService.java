package com.example.major.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.major.model.CustomUserDetail;
import com.example.major.model.User;
import com.example.major.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	UserRepository userrepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userrepo.findUserById(email);
		user.orElseThrow(() -> new UsernameNotFoundException("User not Found!!!"));
		return user.map(CustomUserDetail::new).get();
	}

}
