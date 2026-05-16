package com.jwtdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwtdemo.dto.AuthRequest;
import com.jwtdemo.dto.RegisterRequest;
import com.jwtdemo.service.AuthService;
import com.jwtdemo.service.UserService;
import com.jwtdemo.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	public final AuthenticationManager authenticationManager;
	public final JwtUtil jwtutil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;

	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtutil) {
		this.authenticationManager = authenticationManager;
		this.jwtutil = jwtutil;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request){
		
		try {
			
			return ResponseEntity.ok(authService.login(request));
		} catch (AuthenticationException e) {
			System.out.println(e.getMessage() + " ::sgdfgf");
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body("Invalid credentials");
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request){
		String response = "";
		try {
			response = userService.registerUser(request);
		} catch (Exception e) {
			logger.error("Error occured while registering user :: ", e);
			throw e;
		}
		return ResponseEntity.ok(response);
	}

}
