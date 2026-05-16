package com.jwtdemo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwtdemo.dto.ProfileResponse;

@RestController
public class ProfileController {

    @GetMapping("/profile")
    public ResponseEntity<?> profile() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails =	
                (UserDetails) authentication.getPrincipal();

        /*Map<String, Object> response = new HashMap<>();

        response.put("username", userDetails.getUsername());
        response.put("role", userDetails.getAuthorities());*/
        
        ProfileResponse response = new ProfileResponse(userDetails.getUsername(), userDetails.getAuthorities());

        return ResponseEntity.ok(response);
    }
}

// or we can just write as below - Cleaner Modern Approach (Recommended)
//Spring automatically provides current authentication.

/*@GetMapping("/profile")
public ResponseEntity<?> profile(Authentication authentication) {

    UserDetails userDetails =
            (UserDetails) authentication.getPrincipal();

    Map<String, Object> response = new HashMap<>();

    response.put("username", userDetails.getUsername());

    response.put("role", userDetails.getAuthorities());

    return ResponseEntity.ok(response);
}*/
