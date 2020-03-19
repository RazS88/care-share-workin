package com.raz.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RestConfig implements WebMvcConfigurer {

	@Bean(name="tokens")
	public Map<String ,ClientSession>tokenMap(){
		return new HashMap<>();
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("http://localhost:4200")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTION")
				.allowedHeaders("Access-Control-Allow-Credentials", "HttpHeaders", "Content-Type", "Accept")
				.allowCredentials(true);
	}
}
