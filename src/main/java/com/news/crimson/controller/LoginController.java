package com.news.crimson.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.crimson.entity.User;
import com.news.crimson.service.LoginService;

@RestController
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@PostMapping(path = "/login")
	public String login() {
		User user = loginService.loginAccount();
		return "login success";
	}
}
