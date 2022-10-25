package com.news.crimson.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.crimson.entity.User;
import com.news.crimson.service.ProfileService;

@RestController

public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
	@GetMapping(path = "/profile")
	public User viewProfile() {
		return profileService.getProfileDetails();
	}
}
