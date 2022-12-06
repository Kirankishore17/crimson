package com.news.crimson.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.news.crimson.model.TopicNewsInfo;

public interface TopicNewsRepo extends JpaRepository<TopicNewsInfo, Integer>{

	@Query(value = "select * from topic_news_info where topic=:topic order by id desc limit 30", nativeQuery = true)
	public List<TopicNewsInfo> getNewsByTopic(String topic);

}
