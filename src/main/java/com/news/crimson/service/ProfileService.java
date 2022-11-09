package com.news.crimson.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.news.crimson.dao.UserDao;
import com.news.crimson.entity.User;
import com.news.crimson.model.ProfileInfo;

@Service
public class ProfileService {
	
	@Autowired
	private UserDao userDao;

	public User getProfileDetails() {
		return new User();
	}

	public String updateProfileDetails(ProfileInfo profileInfo) {

		return "Profile updated successfully!";
	}

	public String blockProfile(Integer userId, Integer blockId) {

		return "Profile blocked successfully!";
	}

	public String followProfile(Integer userId, Integer followId) {
		return "Profile followed successfully!";
	}

	public String addFriend(Integer userId, Integer friendId) {
		return "Friend request successful!";
	}
	
	public List<User> getAllProfiles(){
		return userDao.findAll();
	}
}
