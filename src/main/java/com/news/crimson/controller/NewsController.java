package com.news.crimson.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.news.crimson.entity.SharedNewsInfo;
import com.news.crimson.exception.ServiceException;
import com.news.crimson.model.NewsInfo;
import com.news.crimson.model.TopicNewsInfo;
import com.news.crimson.service.NewsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*") 
public class NewsController {

	@Autowired
	private NewsService newsService;

	@GetMapping(path = "/news/home", produces = "application/json")
	public List<NewsInfo> getNews() throws ServiceException {
		return newsService.getNews();
	}

	@GetMapping(path = "/news/search", produces = "application/json")
	public List<NewsInfo> searchNews(@RequestParam(required = true) String q,
			@RequestParam(required = false) String location) throws ServiceException {
		return newsService.searchNews(q, location);
	}

	@GetMapping(path = "/news/topic", produces = "application/json")
	public List<TopicNewsInfo> getNewsByTopic(@RequestParam(required = true) String q) throws ServiceException {
		return newsService.getNewsByTopic(q);
	}
	
	@PostMapping(path = "/news/readlater", produces = "application/json")
	public String bookmarkNews(@RequestParam(required = true) Integer userId, @RequestBody NewsInfo newsInfo) throws ServiceException {
		return newsService.bookmarkNews(userId, newsInfo);
	}

	@GetMapping(path = "/news/readlater", produces = "application/json")
	public String getBookmarkedNews(@RequestParam(required = true) Integer userId) throws ServiceException {
		return newsService.getBookmarkedNews(userId);
	}
	
	@PostMapping(path = "/news/feed", produces = "application/json")
	public String shareNews(@RequestBody SharedNewsInfo sharedNewsInfo) throws ServiceException {
		return newsService.shareNews(sharedNewsInfo);
	}

	@GetMapping(path = "/news/feed", produces = "application/json")
	public List<SharedNewsInfo> getFeed(@RequestParam(required = true) Integer userId) throws ServiceException {
		return newsService.getFeed(userId);
	}

}
