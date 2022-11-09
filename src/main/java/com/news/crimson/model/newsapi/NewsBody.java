package com.news.crimson.model.newsapi;

import lombok.Data;
import lombok.NonNull;

@Data
public class NewsBody {

	private String version;
	private NewsChannel channel;
}
