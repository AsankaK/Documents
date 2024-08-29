package com.asankak.documents.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.asankak.documents.config.AppConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.security.core.userdetails.User.withUsername;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class DocuUserDetailsService implements UserDetailsService {
	
	private final AppConfig config;
	private final PasswordEncoder encoder = new BCryptPasswordEncoder(12);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if(config.getAuthentication().getUserCredentials().containsKey(username)) {
			return withUsername(username)
					.password(encoder.encode(config.getAuthentication().getUserCredentials().get(username)))
					.authorities("ROLE_USER").build();
		}
		
		throw new UsernameNotFoundException("User "+username+" not found");
	}

}
