package com.news.crimson.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.crimson.exception.ServiceException;
import com.news.crimson.model.NewsInfo;
import com.news.crimson.model.newsapi.ImageBody;
import com.news.crimson.model.newsapi.NewsBody;
import com.news.crimson.model.newsapi.NewsItem;

@Service
public class NewsInfoService {

	@Value("${news.article.image}")
	private String imageNewsUrl;

	@Autowired
	private RestTemplate restTemplate;

	public String getImageUrl(String title) throws ServiceException {
		try {
			String url = this.imageNewsUrl.replace("{TITLE}", title.replace(" ", "%"));
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<Void> entity = new HttpEntity<>(headers);
			headers.add("X-RapidAPI-Key", "b562bea377msh8176b6a29feb2e3p168925jsn964fc69a8b05");
			headers.add("X-RapidAPI-Host", "bing-image-search1.p.rapidapi.com");
			ResponseEntity<ImageBody> imageBody = restTemplate.exchange(url, HttpMethod.GET, entity, ImageBody.class);
			return imageBody.getBody().getValue().get(0).getContentUrl();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public List<NewsInfo> getNewsInfoListFromRssNews(NewsBody rssNewsBody) throws ServiceException {
		List<NewsInfo> news = new ArrayList<>();
		if (rssNewsBody.getChannel() != null && rssNewsBody.getChannel().getItem() != null) {
			List<NewsItem> contentList = rssNewsBody.getChannel().getItem();
			int count = 1;
			for (NewsItem item : contentList) {
				NewsInfo info = new NewsInfo();
				info.setId(count);
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String, String> source = objectMapper.convertValue(item.getSource(), HashMap.class);
				if (source.keySet().contains("url"))
					info.setSourceUrl(source.get("url"));
				if (source.keySet().contains(""))
					info.setSourceName(source.get(""));
				String title = item.getTitle().substring(0, item.getTitle().length() - (2 + source.get("").length()));
				info.setHeadlines(title);
				info.setCover(getImageUrl(title));
				info.setPublishDate(item.getPubDate());
				info.setArticleUrl(item.getLink());
				count++;
				news.add(info);
			}
		}
		return news;
	}

}
