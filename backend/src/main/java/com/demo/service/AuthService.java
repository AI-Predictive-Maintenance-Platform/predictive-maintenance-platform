package com.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.dto.RegisterRequest;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.repository.UserRepository;

@Service
public class AuthService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public User register(RegisterRequest req) {
		if(userRepository.existsByUserName(req.userName())) {
			throw new IllegalArgumentException("Username already taken");
		}
		else if(userRepository.existsByEmail(req.email())) {
			throw new  IllegalArgumentException("Email already registered");
		}
			
		String hashed = passwordEncoder.encode(req.password());
		User user = new User(req.userName(), req.email(), hashed, Role.VIEWER);
		return userRepository.save(user);
		
	}
	
	
	
}
