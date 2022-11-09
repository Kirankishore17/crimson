package com.news.crimson.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.news.crimson.entity.User;
import com.news.crimson.model.ProfileInfo;
import com.news.crimson.model.ResponseInfo;
import com.news.crimson.service.ProfileService;

@RestController

public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
	@GetMapping(path = "/profile")
	public User viewProfile() {
		return profileService.getProfileDetails();
	}
	
	@PutMapping(path = "/profile")
	public ResponseEntity updateProfile(@RequestBody ProfileInfo profileInfo) {
		HttpStatus statusCode;
		if(profileInfo.getId() == null) {
			statusCode = HttpStatus.CREATED;
		} else {
			statusCode = HttpStatus.OK;
		}
		String message = profileService.updateProfileDetails(profileInfo);
		return new ResponseEntity<String>(message, statusCode);

	}

	@DeleteMapping(path = "/profile/block")
	public ResponseEntity blockProfile(@RequestParam("userId") Integer userId, @RequestParam("blockId") Integer blockId) {
		String message = profileService.blockProfile(userId, blockId);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@GetMapping(path = "/profile/follow")
	public ResponseEntity followProfile(@RequestParam("userId") Integer userId, @RequestParam("followId") Integer followId) {
		String message = profileService.followProfile(userId, followId);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@GetMapping(path = "/profile/friend")
	public ResponseEntity addFriend(@RequestParam("userId") Integer userId, @RequestParam("friendId") Integer friendId) {
		String message = profileService.addFriend(userId, friendId);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@GetMapping(path = "/profile/all")
	public List<User> getAllProfile() {
		return profileService.getAllProfiles();
	}
	
}
