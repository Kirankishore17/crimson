package com.news.crimson.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.news.crimson.entity.User;
import com.news.crimson.service.ProfileService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
	
	@GetMapping(path = "/profile/check", produces = "application/json")
	public Boolean unblockProfile(@RequestParam("email") String email, @RequestParam("loginSource") String loginSource) {
		return profileService.checkProfile(email,loginSource);
	}
		

	@PostMapping(path = "/profile/block", produces = "application/json")
	public ResponseEntity<String> blockProfile(@RequestParam("userId") Integer userId, @RequestParam("blockId") Integer blockId) {
		String message = profileService.blockProfile(userId, blockId);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/profile/unblock", produces = "application/json")
	public ResponseEntity<String> unblockProfile(@RequestParam("userId") Integer userId, @RequestParam("blockId") Integer blockId) {
		String message = profileService.unblockProfile(userId, blockId);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@PostMapping(path = "/profile/follow", produces = "application/json")
	public ResponseEntity<String> followProfile(@RequestParam("userId") Integer userId, @RequestParam("followerId") Integer followerId) {
		String message = profileService.followProfile(userId, followerId);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/profile/unfollow", produces = "application/json")
	public ResponseEntity<String> unfollowProfile(@RequestParam("userId") Integer userId, @RequestParam("followerId") Integer followerId) {
		String message = profileService.unfollowProfile(userId, followerId);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@PutMapping(path = "/profile/sendFriendRequest", produces = "application/json")
	public ResponseEntity<String> addFriendRequest(@RequestParam("userId") Integer userId, @RequestParam("friendId") Integer friendId) {
		String message = profileService.addFriendRequest(userId, friendId);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	
	@PutMapping(path = "/profile/acceptFriendRequest", produces = "application/json")
	public ResponseEntity<String> addFriend(@RequestParam("userId") Integer userId, @RequestParam("friendId") Integer friendId) {
		String message = profileService.addFriend(userId, friendId);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/profile/unfriend", produces = "application/json")
	public ResponseEntity<String> deleteFriend(@RequestParam("userId") Integer userId, @RequestParam("friendId") Integer friendId) {
		String message = profileService.deleteFriend(userId, friendId);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	
}
