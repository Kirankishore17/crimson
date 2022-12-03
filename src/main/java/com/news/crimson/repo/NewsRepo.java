package com.news.crimson.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.news.crimson.model.NewsInfo;

public interface NewsRepo extends JpaRepository<NewsInfo, Integer>{

	@Query(value = "select * from News_Info order by id desc limit 50", nativeQuery = true)
	public List<NewsInfo> getNews();

}
