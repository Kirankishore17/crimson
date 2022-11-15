package com.news.crimson;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RequiredBean {

	@Bean
	public RestTemplate getRestTemplateObject() {
		return new RestTemplate();
	}
	
	@Bean
	public ObjectMapper getObjectMapperObject() {
		return new ObjectMapper();
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
		        registry.addMapping("/**");
			}
		};
	}
}
