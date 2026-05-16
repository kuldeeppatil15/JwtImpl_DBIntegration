package com.jwtdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jwtdemo.filter.JwtFilter;
import com.jwtdemo.util.JwtAccessDeniedHandler;
import com.jwtdemo.util.JwtAuthenticationEntryPoint;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	/*
	 * @Autowired private JwtFilter jwtFilter;
	 */
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.csrf(csrf -> csrf.disable())
		.sessionManagement(session -> 
			session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		)
		.authorizeHttpRequests(auth -> auth
				.requestMatchers("/auth/**","/hello","/test-token").permitAll()
				.requestMatchers("/admin").hasRole("ADMIN")
				.requestMatchers("/user").hasRole("USER")
				.anyRequest().authenticated()
		)
		//As spring by default sends 403 for every request, 
		//we needs to handle this as per below to get 401 and 403 behavior correctly
		//Without reusable custom classes - Method 1
		/*.exceptionHandling(exception -> exception
				.authenticationEntryPoint((request, response, authException) -> {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
				})
				.accessDeniedHandler((request, response, accessDeniedException) -> {
					response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
				})
		)*/
		//With reusable custom classes - Method 2
		.exceptionHandling(ex -> ex
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)
		)
		
//		.formLogin(form -> form.disable());
		.addFilterBefore(getJwtFilter(), UsernamePasswordAuthenticationFilter.class);
		
		return http.build(); 
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
		return config.getAuthenticationManager();
	}
	
	/*@Bean
	public UserDetailsService userDetailsService() {
		
		UserDetails user = User.withUsername("user")
				.password(passwordEncoder().encode("User123"))
				.roles("USER")
				.build();
		
		UserDetails admin = User.withUsername("admin")
				.password(passwordEncoder().encode("admin123"))
				.roles("ADMIN")
				.build();
		
		UserDetails manager = User.withUsername("manager")
				.password(passwordEncoder().encode("manager123"))
				.roles("MANAGER")
				.build();
		
		UserDetails managerWithAuth = User.withUsername("manager2")
				.password(passwordEncoder().encode("manager321"))
				.roles("MANAGER")
				.authorities("READ", "ROLE_MANAGER")
				.build();
		
		return new InMemoryUserDetailsManager(user, admin, manager, managerWithAuth);
		
	}*/
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public JwtFilter getJwtFilter() {
		return new JwtFilter();
	}
	
	//For encoded password generation
	/*public static void main(String[] args) {

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        String password = encoder.encode("user123");

        System.out.println(password);
    }*/

}
