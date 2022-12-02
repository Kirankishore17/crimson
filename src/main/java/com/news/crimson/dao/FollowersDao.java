package com.news.crimson.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.news.crimson.entity.FollowerProfile;
import com.news.crimson.repo.FollowersRepo;

@Repository("FollowersRepo")
public class FollowersDao {
	
	@Autowired
	FollowersRepo repo;
	
	public String addFollower(FollowerProfile follower) {
		
		repo.save(follower);
		return "Follower added";		
	}

	public String deleteFollower(FollowerProfile follower) {
				
		repo.delete(follower);
		return "Connection unfollowed";
		
	}
	
	public FollowerProfile findFollowerById(Integer userId, Integer followerId) {
		 
		 List<FollowerProfile> followerList = repo.getFollowersByUserIdAndFollowerId(userId, followerId);
		 if(followerList.size() == 1)
			 return followerList.get(0);
		 else 
			 return null;
					
	}	
	
	
}
