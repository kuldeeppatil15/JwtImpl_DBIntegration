package com.jwtdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwtdemo.util.JwtUtil;

@RestController
@RequestMapping
public class JwtTestController {
	
	@Autowired
	private JwtUtil jstUtil;
	
	@GetMapping("/test-token")
	public String testGenToken() {
		return jstUtil.generateToken("User","USER");
	}

}
