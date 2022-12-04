package com.news.crimson.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.news.crimson.entity.SharedNewsInfo;

public interface ShareNewsRepo extends JpaRepository<SharedNewsInfo, Integer>{

	@Query(value = "select * from shared_news_info u where u.user_id in :ids", nativeQuery = true)
	public List<SharedNewsInfo> getFeed(@Param("ids") List<Integer> ids);

	@Query(value = "select count(*) from shared_news_info u where u.user_id = :userId and u.headlines = :headlines", nativeQuery = true)
	public int countOccurance(@Param("userId") String userId, @Param("headlines") String headlines);

}

