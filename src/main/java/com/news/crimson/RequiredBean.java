package com.news.crimson;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
}
