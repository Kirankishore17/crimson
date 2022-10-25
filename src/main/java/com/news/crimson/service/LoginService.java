package com.news.crimson.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.news.crimson.entity.User;

@Service
public class LoginService {
	
	@Autowired
	private ProfileService profileService;
	
	public User loginAccount() {
		User user = profileService.getProfileDetails();
		return user;
	}

}
