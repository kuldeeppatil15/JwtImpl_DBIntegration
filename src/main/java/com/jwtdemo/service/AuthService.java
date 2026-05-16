package com.jwtdemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.jwtdemo.dto.AuthRequest;
import com.jwtdemo.dto.AuthResponse;
import com.jwtdemo.util.JwtUtil;

@Service
public class AuthService {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
	
	public final AuthenticationManager authenticationManager;
	public final JwtUtil jwtutil;
	
	public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtutil) {
		this.authenticationManager = authenticationManager;
		this .jwtutil = jwtutil;
	}
	
	public AuthResponse login(AuthRequest request) {
		logger.info("Authentication request received");
		logger.info("Login attempt for user: {}", request.getUserName());
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							request.getUserName(), 
							request.getPassword())
					);
			
			if (authentication.isAuthenticated()) {
				String token = jwtutil.generateToken(request.getUserName(), "DontKnow");
				logger.info("User authenticated successfully: {}", request.getUserName());
				
				return new AuthResponse(
						"Success",
						request.getUserName(),
						token
						);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error occurred while login", e);
		}
		logger.warn("Invalid login attempt for user: {}", request.getUserName());
		
		throw new RuntimeException("Invalid credentials");
	}

}
