package com.news.crimson.model;

import lombok.Data;

@Data
public class NewsInfo {

	private Integer id;
	private String headlines;
	private String sourceUrl;
	private String sourceName;
	private String articleUrl;
	private String publishDate;
	private String description;
	private String cover;
	
}
