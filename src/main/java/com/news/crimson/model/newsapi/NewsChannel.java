package com.news.crimson.model.newsapi;

import java.util.List;

import lombok.Data;

@Data
public class NewsChannel {

	private String generator;
	private String title;
	private String link;
	private String language;
	private String  webMaster;
	private String  copyright;
	private String lastBuildDate;
	private String description;
	private List<NewsItem> item;
}
