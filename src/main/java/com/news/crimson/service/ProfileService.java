package com.news.crimson.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.news.crimson.dao.AppLogDao;
import com.news.crimson.dao.BlockedUsersDao;
import com.news.crimson.dao.FollowersDao;
import com.news.crimson.dao.FriendsDao;
import com.news.crimson.dao.UserDao;
import com.news.crimson.entity.AppLog;
import com.news.crimson.entity.BlockedProfile;
import com.news.crimson.entity.FollowerProfile;
import com.news.crimson.entity.FriendProfile;
import com.news.crimson.entity.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProfileService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private FollowersDao followerDao;

	@Autowired
	private FriendsDao friendDao;

	@Autowired
	private BlockedUsersDao blockedUsersDao;

	@Autowired
	private AppLogDao appLogDao;

	public User getProfileDetails(Integer userId) {
		AppLog appLog = new AppLog();
		appLog.setRequestBody("");
		appLog.setDescription("GET : /profile | getProfileDetails | API called to fetch profile details");
		appLog.setUserId(userId.toString());
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		String timestamp = formatter.format(new Date());
		appLog.setTimestamp(timestamp);
		try {
			User res = userDao.findUserById(userId);
			appLog.setType("INFO");
			appLog.setResponse("Profile details sent for user with ID: " + userId);
			appLog.setExceptionMessage("");
			appLogDao.saveLog(appLog);
			return res;
		} catch (Exception e) {
			log.error(e.toString());
			appLog.setType("ERROR");
			appLog.setExceptionMessage(
					e.getMessage().substring(0, 100 > e.getMessage().length() ? e.getMessage().length() : 100));
			appLog.setResponse("");
			appLogDao.saveLog(appLog);
			return new User();
		}
	}

	public User updateProfileDetails(User user) {

		User checkUser = userDao.getUserbyEmailAndLoginSource(user.getEmail(), user.getLoginSource());
		if (user.getFavCategory() == null) {
			if (checkUser.getFavCategory() == null)
				user.setFavCategory("[]");
			else
				user.setFavCategory(checkUser.getFavCategory());

		}

//		if(checkUser.getId() != null) {
//				user.setFavCategory(checkUser.getFavCategory());
//			
//		} else {		
//		
//			if(checkUser.getFavCategory() == null) {
//				user.setFavCategory("[]");
//			}
//		}
		return userDao.updateProfile(user);

	}

	public Boolean checkProfile(String email, String loginSource) {

		return userDao.getUser(email, loginSource);
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
		String message = blockedUsersDao.deleteBlock(blockedUser);
		return message;
	}

	public String followProfile(Integer userId, Integer followId) {
		AppLog appLog = new AppLog();
		appLog.setRequestBody("");
		appLog.setDescription("POST : /profile/follow | followProfile | API called to follow a profile");
		appLog.setUserId(userId.toString());
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		String timestamp = formatter.format(new Date());
		appLog.setTimestamp(timestamp);
		try {
			User followerUser = userDao.findUserById(followId);
			String fullName = followerUser.getFirstName() + " " + followerUser.getLastName();
			FollowerProfile follower = new FollowerProfile();
			follower.setFollowerId(followId);
			follower.setFollowerProfileName(fullName);
			follower.setUserId(userId);
			String message = followerDao.addFollower(follower);
			appLog.setType("INFO");
			appLog.setResponse(message);
			appLog.setExceptionMessage("");
			appLogDao.saveLog(appLog);
			return message;
		} catch (Exception e) {
			return "Unable to follow profile";
		}
	}

	public String unfollowProfile(Integer userId, Integer followerId) {

		FollowerProfile follower = followerDao.findFollowerById(userId, followerId);
		String message = followerDao.deleteFollower(follower);
		return message;
	}

	public String addFriend(Integer userId, Integer friendId) {
		AppLog appLog = new AppLog();
		appLog.setRequestBody("");
		appLog.setDescription("POST : /profile/follow | followProfile | API called to add friend a profile");
		appLog.setUserId(userId.toString());
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		String timestamp = formatter.format(new Date());
		appLog.setTimestamp(timestamp);
		try {

			FriendProfile friendUser = friendDao.findFriendsByIds(friendId,userId);
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
			appLog.setType("INFO");
			appLog.setResponse(message);
			appLog.setExceptionMessage("");
			appLogDao.saveLog(appLog);
			return message;
		} catch (Exception e) {
			return "Unable to send friend request";
		}
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

		FriendProfile friend = friendDao.findFriendsById(userId, friendId);
		String message = friendDao.deleteFriend(friend);
		FriendProfile friend2 = friendDao.findFriendsById(friendId, userId);
		String message2 = friendDao.deleteFriend(friend2);

		return message;
	}

	public List<User> getProfileByLocation(String location) {

		return userDao.getProfileByLocation(location.toUpperCase());
	}

	public List<User> getProfileByNewsCategory(String category) {

		return userDao.getProfileByNewsCategory(category.toUpperCase());
	}

	public List<HashMap<String, String>> getRequests(Integer userId) {

		List<Integer> userIDs = friendDao.getPendingFriendRequests(userId);
		List<HashMap<String, String>> requestedUsers = new ArrayList<HashMap<String, String>>();
		User user = new User();
		String name;
		for (int i = 0; i < userIDs.size(); i++) {
			user = userDao.findUserById(userIDs.get(i));
			// System.out.println("Users id =" + userIDs.get(i));
			name = user.getFirstName() + " " + user.getLastName();
			HashMap<String, String> map = new HashMap<>();
			map.put("userId", user.getId().toString());
			map.put("name", name);
			requestedUsers.add(map);
			// System.out.println(requestedUsers.get(user.getId()));
		}
		return requestedUsers;
	}

}
