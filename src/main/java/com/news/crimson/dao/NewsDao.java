package com.news.crimson.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.news.crimson.entity.BookmarkedNews;
import com.news.crimson.model.NewsInfo;
import com.news.crimson.repo.BookmarkedNewsRepo;
import com.news.crimson.repo.NewsRepo;

@Repository
public class NewsDao {

	@Autowired
	private BookmarkedNewsRepo bookmarkedNewsRepo;
	
	@Autowired
	private NewsRepo newsRepo;

	public void bookmarkNews(BookmarkedNews bookmark) {
		bookmarkedNewsRepo.save(bookmark);
	}

	public String getBookmarkedNews(Integer userId) {
		return bookmarkedNewsRepo.getBookmarkedNewsByUserId(userId);
	}

	public List<NewsInfo> getNews() {
		return newsRepo.getNews();
	}

	public List<NewsInfo> saveNews(List<NewsInfo> list) {
		
		return newsRepo.saveAll(list);
	}
	
	

}
