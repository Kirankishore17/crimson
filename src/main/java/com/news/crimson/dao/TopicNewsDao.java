package com.news.crimson.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.news.crimson.model.TopicNewsInfo;
import com.news.crimson.repo.TopicNewsRepo;

@Repository
public class TopicNewsDao {

	@Autowired
	private TopicNewsRepo topicNewsRepo;

	public List<TopicNewsInfo> getBookmarkedNews(String topic) {
		return topicNewsRepo.getNewsByTopic(topic);
	}

	public List<TopicNewsInfo> saveTopicNews(List<TopicNewsInfo> topicList) {
		return topicNewsRepo.saveAll(topicList);
	}

	public List<TopicNewsInfo> getNewsByTopic(String topic) {
		return topicNewsRepo.getNewsByTopic(topic);
	}

}
