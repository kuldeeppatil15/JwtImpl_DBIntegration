package com.jwtdemo.dto;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileResponse {
	
	private String username;
	private Collection<?> roles;

}
