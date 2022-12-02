package com.news.crimson.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.news.crimson.entity.FollowerProfile;

@Repository
public interface FollowersRepo extends JpaRepository<FollowerProfile, Integer> {

	
	 @Query(value = "SELECT * FROM FOLLOWER_PROFILE u WHERE u.user_id=:userId AND u.follower_id=:followerId",
	 nativeQuery = true) List<FollowerProfile> getFollowersByUserIdAndFollowerId(@Param("userId")
	 Integer userId, @Param("followerId") Integer followerId);
	
}