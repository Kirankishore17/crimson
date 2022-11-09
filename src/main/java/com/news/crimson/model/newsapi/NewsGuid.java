package com.news.crimson.model.newsapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class NewsGuid {

	private String isPermaLink;
	
	@JsonProperty(value = "")
	private String key;
}
