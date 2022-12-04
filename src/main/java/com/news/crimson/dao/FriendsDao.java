package com.news.crimson.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.news.crimson.entity.FriendProfile;
import com.news.crimson.repo.FriendsRepo;

@Repository("FriendsRepo")
public class FriendsDao {
	
	@Autowired
	FriendsRepo repo;
	
	public String addFriend(FriendProfile friend) {
		
		repo.save(friend);
        return "Friend added to your connections";
	}
	
	public String addFriendRequest(FriendProfile friend) {
				
		repo.save(friend);
        return "Friend request sent";
	}

	public String deleteFriend(FriendProfile friend) {
		
		if(friend.getFriendStatus().equalsIgnoreCase("confirmed")) {
			
			repo.delete(friend);
			return "Connection unfriended";	
			
		} else {
			
			return "Connection not unfriended";
		}
		
			
	}
	
	public FriendProfile findFriendsById(Integer userId, Integer friendId) {
		 
		 List<FriendProfile> friendList = repo.getFriendsByUserId(userId);
		 FriendProfile friend = new FriendProfile();
			for(int i=0; i<friendList.size(); i++) {
				if(friendList.get(i).getFriendId() == friendId) {
					return friendList.get(i);
				} else
					continue;					
			}
		return friend;					
	}
	
	public FriendProfile findPendingFriendsByIds(Integer userId, Integer friendId) {
		 
		 List<FriendProfile> friendList = repo.getFriendsByUserIdAndFriendId(userId,friendId);
		 if(friendList.size() == 1) {
			 return friendList.get(0);
		 } else return null; 					
	}
	
	public List<Integer> getPendingFriendRequests(Integer userId) {
		
		return repo.getUserRequestsById(userId);
		
	}
}