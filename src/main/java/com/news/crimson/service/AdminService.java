package com.news.crimson.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.news.crimson.dao.UserDao;
import com.news.crimson.entity.User;

@Service
public class AdminService {

	@Autowired
	private UserDao userDao;
	
	public List<User> getAllProfiles() {
		return userDao.findAll();
	}
	
	public Integer getnumberOfUsers() {
		return userDao.numberOfUsers();
	}
	
	public List<HashMap<String,String>> getCategoryCount(){
		return userDao.categoryCount();
	}
	
}