package com.news.crimson.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.crimson.exception.ServiceException;
import com.news.crimson.model.NewsInfo;
import com.news.crimson.service.FetchNewsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FetchNewsController {

	@Autowired
	private FetchNewsService fetchNewsService;

	@GetMapping(path = "/fetch/news/home", produces = "application/json")
	public List<NewsInfo> getNews() throws ServiceException {
		return fetchNewsService.fetchNews();
	}

	@GetMapping(path = "/fetch/news/topic", produces = "application/json")
	public String getNewsByTopic() throws ServiceException {
		return fetchNewsService.fetchNewsByTopic();
	}

}
