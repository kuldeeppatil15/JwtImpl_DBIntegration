package com.jwtdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.jwtdemo.entity.UserEntity;
import com.jwtdemo.service.UserService;

@RestController
public class SampleController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/getUser/{username}")
	public ResponseEntity<?> getUser(@PathVariable("username") String username){
		
		UserEntity response = userService.getUser(username);
		
		return ResponseEntity.ok(response);
	}

	@GetMapping("/")
	public String getGreeting() {
		return "welcome";
	}

	@GetMapping("/hello")
	public String getGreetingHello() {
		return "welcome and hello";
	}

	@GetMapping("/secure")
	public String secure() {
		return "Secure API";
	}

	@GetMapping("/user")
	public String user() {
		return "User API";
	}

	@GetMapping("/admin")
	public String admin() {
		return "Admin API";
	}

	@GetMapping("/manager")
	public String manager() {
		return "Manager API";
	}

	@GetMapping("/read")
	public String read() {
		return "Read API";
	}

}

