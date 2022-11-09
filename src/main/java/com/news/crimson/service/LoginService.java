package com.news.crimson.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.news.crimson.dao.UserDao;
import com.news.crimson.entity.User;


@Service
public class LoginService {
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private UserDao userDao;

	public User loginAccount(User user) {
		userDao.addUser(user);
//		User user = profileService.getProfileDetails();
		List<User> userList = profileService.getAllProfiles();
		return new User();
	}

}
