package com.news.crimson.service;

import java.io.IOException;
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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.news.crimson.exception.ServiceException;
import com.news.crimson.model.NewsInfo;
import com.news.crimson.model.newsapi.ImageBody;
import com.news.crimson.model.newsapi.ImageItem;
import com.news.crimson.model.newsapi.NewsBody;
import com.news.crimson.model.newsapi.NewsItem;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NewsInfoService {

	@Value("${news.article.image}")
	private String imageNewsUrl;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	public String getImageUrl(String title) throws ServiceException {
		try {
			String url = this.imageNewsUrl.replace("{TITLE}", title.replace(" ", "%"));
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<Void> entity = new HttpEntity<>(headers);
			headers.add("X-RapidAPI-Key", "b208ec3a1dmsh0ab401d889d233bp163c20jsn326818daa362");
			headers.add("X-RapidAPI-Host", "bing-image-search1.p.rapidapi.com");
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			ResponseEntity<ImageBody> imageBody = restTemplate.exchange(url, HttpMethod.GET, entity, ImageBody.class);
			List<ImageItem> list = imageBody.getBody().getValue();
			if (!list.isEmpty())
				return list.get(0).getContentUrl();
			else
				return "https://static.vecteezy.com/system/resources/previews/000/228/896/original/vector-breaking-news-alert-background-in-red-theme.jpg";
		} catch (Exception e) {
			log.error(e.getMessage());
			return "https://static.vecteezy.com/system/resources/previews/000/228/896/original/vector-breaking-news-alert-background-in-red-theme.jpg";
		}
	}

	public List<NewsInfo> getNewsInfoListFromRssNews(NewsBody rssNewsBody, String type) throws ServiceException {
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
				if (type.equalsIgnoreCase("search"))
					info.setCover("https://static.vecteezy.com/system/resources/previews/000/228/896/original/vector-breaking-news-alert-background-in-red-theme.jpg");
				else if(type.equalsIgnoreCase("topic"))
					info.setCover("https://static.vecteezy.com/system/resources/previews/000/228/896/original/vector-breaking-news-alert-background-in-red-theme.jpg");
				else
					info.setCover(getImageUrl(title));
				int lenDate = item.getPubDate().length() - 13;
				info.setPublishDate(item.getPubDate().substring(0, lenDate));
				info.setArticleUrl(item.getLink());
				count++;
				news.add(info);
			}
		}
		return news;
	}

	public String getJsonFromXmlString(String xmlBody) throws IOException {
		XmlMapper xmlMapper = new XmlMapper();
		JsonNode node = xmlMapper.readTree(xmlBody.getBytes());
		String json = objectMapper.writeValueAsString(node);
		return json;
	}

}
