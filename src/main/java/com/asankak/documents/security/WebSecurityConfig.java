package com.asankak.documents.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	private final DocuUserDetailsService userDetailsService;
	
@Bean
SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
	
	//csrf disabled since cookies are not used for authentication
	
	http.csrf(csrf -> csrf.disable()).httpBasic(withDefaults())
	.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	.authorizeHttpRequests(auth -> auth.requestMatchers("/api/*").authenticated().anyRequest().permitAll());
	
	
	return http.build();
	
}

@Bean
AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	
	final List<GlobalAuthenticationConfigurerAdapter> adapters = new ArrayList<>();
	
	adapters.add(new GlobalAuthenticationConfigurerAdapter() {
		@Override
		public void configure(AuthenticationManagerBuilder authentication) throws Exception{
			authentication.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		}

		
	});
	
	return authConfig.getAuthenticationManager();
	
}

@Bean
PasswordEncoder passwordEncoder() {
	
	return new BCryptPasswordEncoder(12);
}

	

}
