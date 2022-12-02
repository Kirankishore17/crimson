package com.news.crimson.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.news.crimson.entity.BlockedProfile;
import com.news.crimson.repo.BlockedUsersRepo;

@Repository("BlockedUsersRepo")
public class BlockedUsersDao {
	
	@Autowired
	BlockedUsersRepo repo;
	
	public String addBlock(BlockedProfile blockUser) {
		
		repo.save(blockUser);
		return "Connection Blocked";		
	}
	
	public String deleteBlock(BlockedProfile blockedUser) {
		
		repo.delete(blockedUser);
		return "Connection unblocked";
		
	}
	
	public BlockedProfile findBlockedById(Integer userId, Integer blockId) {
		
		List<BlockedProfile> blockList = repo.getBlockedByUserIdAndBlockId(userId,blockId);
		 if(blockList.size() == 1) {
			 return blockList.get(0);
		 } else return null; 
	}
	
}

