package com.news.crimson.controller;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.crimson.entity.AppLog;
import com.news.crimson.entity.User;
import com.news.crimson.service.AdminService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
	public List<HashMap<String,String>> getTotalCategoryCount() {
		return adminService.getCategoryCount();
	}
	
//	@GetMapping(path = "/application/logs/download")
//	public File downloadApplicationLogs() {
//		InputStream in = adminService.downloadApplicationLogs();
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//	    headers.add("Pragma", "no-cache");
//	    headers.add("Expires", "0");
//
//	    return ResponseEntity
//	            .ok()
//	            .headers(headers)
//	            .contentLength(pdfFile.contentLength())
//	            .contentType(MediaType.parseMediaType("application/octet-stream"))
//	            .body(new InputStreamResource(in));
//
//	}

	@GetMapping(path = "/application/logs", produces = "application/json")
	public List<AppLog> getApplicationLogs() {
		return adminService.getApplicationLogs();
	}
}