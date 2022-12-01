package com.news.crimson.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.news.crimson.entity.BlockedProfile;

@Repository
public interface BlockedUsersRepo extends JpaRepository<BlockedProfile, Integer> {
	
	@Query(value = "SELECT * FROM BLOCKED_PROFILE u WHERE u.user_id=:userId AND u.blocked_id=:blockId",
			 nativeQuery = true) List<BlockedProfile> getBlockedByUserIdAndBlockId(@Param("userId")
			 Integer userId, @Param("blockId")
			 Integer blockId);
	
}