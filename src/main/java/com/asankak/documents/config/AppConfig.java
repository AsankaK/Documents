package com.asankak.documents.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Configuration
@ConfigurationProperties(prefix = "app")
@Slf4j
@Getter
@Setter
public class AppConfig {

	private final Authentication authentication = new Authentication();
	
	@Getter
	@Setter
	public static class Authentication{
		
		private boolean enabled = true;
		private List<User> users = new ArrayList<>();
		
		private Map<String, String> userCredentials = new HashMap<>();
		
		private User defaultUser = new User();
		
		@Getter
		@Setter
		public static class User{
			private String username = "docMan";
			private String password = "docMan";
		}
	}
	
	@PostConstruct
	public void initialize() {
		if(authentication.isEnabled()) {
			
			if(authentication.getUsers().isEmpty()) {
				authentication.getUsers().add(authentication.getDefaultUser());
			}
			
			for(com.asankak.documents.config.AppConfig.Authentication.User user : authentication.getUsers()) {
				authentication.getUserCredentials().put(user.getUsername(), user.getPassword());
			}
			log.info("Credentials loaded for "+authentication.getUserCredentials().size() + " user/s");
		}else
		{
			log.info("authentication disabled");
		}
	}
}
