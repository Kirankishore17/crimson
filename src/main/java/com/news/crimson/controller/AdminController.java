package com.news.crimson.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.crimson.entity.User;
import com.news.crimson.service.AdminService;

@RestController

public class AdminController {
	
	@Autowired
	private AdminService adminService;
	


	@GetMapping(path = "/admin/profile/all", produces = "application/json")
	public List<User> getAllProfile() {
		return adminService.getAllProfiles();
	}
	
	@GetMapping(path = "/admin/profile/all/count", produces = "application/json")
	public Integer getTotalUsersCount() {
		return adminService.getnumberOfUsers();
	}
	
	@GetMapping(path = "/admin/category/all/count", produces = "application/json")
	public HashMap<String,Integer> getTotalCategoryCount() {
		return adminService.getCategoryCount();
	}
	
	
}