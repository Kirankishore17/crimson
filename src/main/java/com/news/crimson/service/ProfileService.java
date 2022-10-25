package com.news.crimson.service;

import org.springframework.stereotype.Service;

import com.news.crimson.entity.User;

@Service
public class ProfileService {

	public User getProfileDetails() {
		return new User();
	}
}
