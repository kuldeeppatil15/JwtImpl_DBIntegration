package com.jwtdemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwtdemo.dto.RegisterRequest;
import com.jwtdemo.entity.UserEntity;
import com.jwtdemo.exceptions.UserNotFoundException;
import com.jwtdemo.exceptions.UsernameAlreadyExistsException;
import com.jwtdemo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public String registerUser(RegisterRequest request) {
		logger.info("Registration request received :: " + request.getUsername());
		if (userRepository.findByUsername(request.getUsername()).isPresent()) {
			logger.info("Username already exists");
			throw new UsernameAlreadyExistsException("Username already exists");
		}
		
		UserEntity user = new UserEntity();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
//		user.setRole(request.getRole());
		user.setRole("USER");
		userRepository.save(user);
		logger.info("User Registered Successfully");
		return "User Registered Successfully";

	}
	
	public UserEntity getUser(String username) {
		
		UserEntity entity = userRepository.findByUsername(username)
				.orElseThrow(() -> 
				new UserNotFoundException("User not found")
				);
		entity.setPassword("");
		return entity;
		
	}

}
