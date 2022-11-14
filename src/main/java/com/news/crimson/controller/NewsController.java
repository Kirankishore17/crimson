package com.news.crimson.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.news.crimson.exception.ServiceException;
import com.news.crimson.model.NewsInfo;
import com.news.crimson.service.NewsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class NewsController {
	
	@Autowired
	private NewsService newsService;

	@GetMapping("/news/home")
	public List<NewsInfo> getNews() throws ServiceException {
		return newsService.getNews();
	}
	
	@GetMapping("/news/search")
	public List<NewsInfo> searchNews(@RequestParam(required = true) String q, @RequestParam(required = false) String location) throws ServiceException {
		return newsService.searchNews(q, location);
	}
	
	@GetMapping("/news/topic")
	public List<NewsInfo> getNewsByTopic(@RequestParam(required = true) String q) throws ServiceException {
		return newsService.getNewsByTopic(q);
	}
}
