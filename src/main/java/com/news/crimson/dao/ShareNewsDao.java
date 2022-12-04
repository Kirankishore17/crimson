package com.news.crimson.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.news.crimson.entity.SharedNewsInfo;
import com.news.crimson.repo.ShareNewsRepo;

@Repository
public class ShareNewsDao {

	@Autowired
	private ShareNewsRepo shareNewsRepo;

	public SharedNewsInfo shareNews(SharedNewsInfo sharedNewsInfo) {
		return shareNewsRepo.save(sharedNewsInfo);
	}

	public List<SharedNewsInfo> getFeed(List<Integer> ids) {
		return shareNewsRepo.getFeed(ids);
	}

	public boolean checkExists(SharedNewsInfo sharedNewsInfo) {
		return (shareNewsRepo.countOccurance(sharedNewsInfo.getUserId(), sharedNewsInfo.getHeadlines()) == 0)? false : true;
	}
	
}
