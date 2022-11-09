package com.news.crimson.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.crimson.model.newsapi.NewsBody;
import com.news.crimson.model.newsapi.NewsItem;

import lombok.Data;

@Data
public class NewsInfo {

	private String headlines;
	private String sourceUrl;
	private String sourceName;
	private String articleUrl;
	private String publishDate;
	private String description;

	public List<NewsInfo> getNewsInfoListFromRssNews(NewsBody rssNewsBody) {
		List<NewsInfo> news = new ArrayList<>();
		if(rssNewsBody.getChannel() != null && rssNewsBody.getChannel().getItem() != null) {
			List<NewsItem> contentList = rssNewsBody.getChannel().getItem();
			for (NewsItem item : contentList) {
				NewsInfo info = new NewsInfo();
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String, String> source = objectMapper.convertValue(item.getSource(), HashMap.class);
				if(source.keySet().contains("url"))
						info.setSourceUrl(source.get("url"));
				if(source.keySet().contains(""))
						info.setSourceName(source.get(""));
				String title = item.getTitle().substring(0, item.getTitle().length() - (2+source.get("").length()));
				info.setHeadlines(title);
				info.setDescription(item.getDescription());
				info.setPublishDate(item.getPubDate());
				info.setArticleUrl(item.getLink());
				news.add(info);
			}
		}
		return news;
	}
}
