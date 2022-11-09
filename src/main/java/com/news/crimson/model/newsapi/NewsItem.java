package com.news.crimson.model.newsapi;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class NewsItem {

	private String title;
	private String link;
	@JsonIgnore
	private Object guid;
	private String pubDate;
	private String description;
	private Object source;
}
