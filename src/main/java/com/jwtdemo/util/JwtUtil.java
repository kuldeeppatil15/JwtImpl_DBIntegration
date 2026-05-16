package com.jwtdemo.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	public static final String SECRET = "mysecretkeymysecretkeymysecretkey";
	
	private Key getSignKey() {
		return Keys.hmacShaKeyFor(SECRET.getBytes());
	}
	
	public String generateToken(String username, String role) {
		return Jwts.builder()
				.setSubject(username)
				.claim("role", role)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*10))	//10 min
				.signWith(getSignKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}
	
	public boolean validateToken(String token, String username) {
		String extractedUsername = extractUsername(token);
		return extractedUsername.equals(username) && !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
		return extractClaims(token).getExpiration().before(new Date());
	}
	
	private Claims extractClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

}


//How to generate key------------------------------------------------------------

//1.Create
//import io.jsonwebtoken.security.Keys;
//import java.security.Key;
//
//Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
//
//System.out.println(io.jsonwebtoken.io.Encoders.BASE64.encode(key.getEncoded()));

//2.Store in properties or env variable

//3. load
//String secret = env.getProperty("jwt.secret");
//Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));



/*
//Generate Token
public String generateToken(String username) {
    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 min
            .signWith(getSignKey(), SignatureAlgorithm.HS256)
            .compact();
}

// Extract Username
public String extractUsername(String token) {
    return extractClaims(token).getSubject();
}

// Validate Token
public boolean validateToken(String token, String username) {
    String extracted = extractUsername(token);
    return (extracted.equals(username) && !isTokenExpired(token));
}

// Check Expiry
private boolean isTokenExpired(String token) {
    return extractClaims(token).getExpiration().before(new Date());
}

// Extract Claims
private Claims extractClaims(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
}*/