package com.news.crimson.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.news.crimson.entity.FriendProfile;

@Repository
public interface FriendsRepo extends JpaRepository<FriendProfile, Integer> {
	
	@Query(value = "SELECT * FROM FRIEND_PROFILE u WHERE u.user_id=:userId",
			 nativeQuery = true) List<FriendProfile> getFriendsByUserId(@Param("userId")
			 Integer userId);
	
	@Query(value = "SELECT * FROM FRIEND_PROFILE u WHERE u.user_id=:userId AND u.friend_id=:friendId",
			 nativeQuery = true) List<FriendProfile> getFriendsByUserIdAndFriendId(@Param("userId")
			 Integer userId, @Param("friendId")
			 Integer friendId);
	
}