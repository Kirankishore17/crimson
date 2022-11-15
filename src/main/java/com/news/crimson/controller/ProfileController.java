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
import com.news.crimson.service.ProfileService;

@RestController

public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
	@GetMapping(path = "/profile", produces = "application/json")
	public User viewProfile(@RequestParam Integer userId) {
		return profileService.getProfileDetails(userId);
	}
	
	@PutMapping(path = "/profile", produces = "application/json")
	public ResponseEntity<User> updateProfile(@RequestBody User user) {
		HttpStatus statusCode;
		if(user.getId() == null) {
			statusCode = HttpStatus.CREATED;
		} else {
			statusCode = HttpStatus.OK;
		}
		User userResponse = profileService.updateProfileDetails(user);
		return new ResponseEntity<User>(userResponse, statusCode);

	}

	@DeleteMapping(path = "/profile/block", produces = "application/json")
	public ResponseEntity blockProfile(@RequestParam("userId") Integer userId, @RequestParam("blockId") Integer blockId) {
		String message = profileService.blockProfile(userId, blockId);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@GetMapping(path = "/profile/follow", produces = "application/json")
	public ResponseEntity followProfile(@RequestParam("userId") Integer userId, @RequestParam("followId") Integer followId) {
		String message = profileService.followProfile(userId, followId);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@GetMapping(path = "/profile/friend", produces = "application/json")
	public ResponseEntity addFriend(@RequestParam("userId") Integer userId, @RequestParam("friendId") Integer friendId) {
		String message = profileService.addFriend(userId, friendId);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@GetMapping(path = "/profile/all", produces = "application/json")
	public List<User> getAllProfile() {
		return profileService.getAllProfiles();
	}
	
}
