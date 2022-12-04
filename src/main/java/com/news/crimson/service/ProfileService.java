package com.news.crimson.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.news.crimson.dao.BlockedUsersDao;
import com.news.crimson.dao.FollowersDao;
import com.news.crimson.dao.FriendsDao;
import com.news.crimson.dao.UserDao;
import com.news.crimson.entity.BlockedProfile;
import com.news.crimson.entity.FollowerProfile;
import com.news.crimson.entity.FriendProfile;
import com.news.crimson.entity.User;

@Service
public class ProfileService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private FollowersDao followerDao;
	
	@Autowired
	private FriendsDao friendDao;
	
	@Autowired
	private BlockedUsersDao blockedUsersDao;

	public User getProfileDetails(Integer userId) {
		return userDao.findUserById(userId);
	}

	public User updateProfileDetails(User user) {
		if(user.getFavCategory() == null) {
			user.setFavCategory("[]");
		}
		return userDao.updateProfile(user);

	}
	public Boolean checkProfile(String email, String loginSource) {
		return userDao.getUser(email,loginSource);
	}

	public String blockProfile(Integer userId, Integer blockId) {
		
		User blockUser = userDao.findUserById(blockId);
		String fullName = blockUser.getFirstName() + " " + blockUser.getLastName();
		BlockedProfile blockedUser = new BlockedProfile();
		blockedUser.setBlockedId(blockId);
		blockedUser.setBlockedProfileName(fullName);
		blockedUser.setUserId(userId);
		String message = blockedUsersDao.addBlock(blockedUser);
		return message;
	}
	
	public String unblockProfile(Integer userId, Integer blockId) {
		
		BlockedProfile blockedUser = blockedUsersDao.findBlockedById(userId, blockId);
		String message =  blockedUsersDao.deleteBlock(blockedUser);
		return message;		
	}

	public String followProfile(Integer userId, Integer followId) {
		
		User followerUser = userDao.findUserById(followId);
		String fullName = followerUser.getFirstName() + " " + followerUser.getLastName();
		FollowerProfile follower = new FollowerProfile();
		follower.setFollowerId(followId);
		follower.setFollowerProfileName(fullName);
		follower.setUserId(userId);
		String message =  followerDao.addFollower(follower);
		return message;
	}
	
	public String unfollowProfile(Integer userId, Integer followerId) {
		
		FollowerProfile follower = followerDao.findFollowerById(userId,followerId);
		String message =  followerDao.deleteFollower(follower);
		return message;		
	}

	public String addFriend(Integer userId, Integer friendId) {
		
		FriendProfile friendUser = friendDao.findPendingFriendsByIds(friendId,userId);
		friendUser.setFriendStatus("Confirmed");
		String message = friendDao.addFriend(friendUser);
		User otherFriend = userDao.findUserById(friendId);
		String fullName = otherFriend.getFirstName() + " " + otherFriend.getLastName();
		FriendProfile friend = new FriendProfile();	
		friend.setFriendId(friendId);
		friend.setFriendProfileName(fullName);
		friend.setFriendStatus("Confirmed");
		friend.setUserId(userId);
		String message2 = friendDao.addFriend(friend);
		return message;
	}
	
	public String addFriendRequest(Integer userId, Integer friendId) {
		
		User friendUser = userDao.findUserById(friendId);
		String fullName = friendUser.getFirstName() + " " + friendUser.getLastName();
		FriendProfile friend = new FriendProfile();	
		friend.setFriendId(friendId);
		friend.setFriendProfileName(fullName);
		friend.setFriendStatus("Pending");
		friend.setUserId(userId);
		String message = friendDao.addFriendRequest(friend);
		return message;
	}
	
	public String deleteFriend(Integer userId, Integer friendId) {

		FriendProfile friend = friendDao.findFriendsById(userId,friendId);
		String message =  friendDao.deleteFriend(friend);
		FriendProfile friend2 = friendDao.findFriendsById(friendId,userId);
		String message2 =  friendDao.deleteFriend(friend2);
		
		return message;
	}

	public List<User> getProfileByLocation(String location) {
		return userDao.getProfileByLocation(location.toUpperCase());

	}

	public List<User> getProfileByNewsCategory(String category) {
		return userDao.getProfileByNewsCategory(category.toUpperCase());
	}

	
}
