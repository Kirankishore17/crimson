package com.news.crimson.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.news.crimson.exception.ServiceException;
import com.news.crimson.model.NewsCategory;
import com.news.crimson.model.NewsInfo;
import com.news.crimson.model.newsapi.ImageBody;
import com.news.crimson.model.newsapi.NewsBody;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NewsService {

	@Value("${rss.news.search}")
	private String rssNewsSearch;

	@Value("${rss.news.home}")
	private String rssNewsHome;

	@Value("${rss.news.topic}")
	private String rssNewsTopic;

	@Autowired
	private NewsInfoService newsInfoService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private RestTemplate restTemplate;

	private String getJsonFromXmlString(String xmlBody) throws IOException {
		XmlMapper xmlMapper = new XmlMapper();
		JsonNode node = xmlMapper.readTree(xmlBody.getBytes());
		String json = objectMapper.writeValueAsString(node);
		return json;

	}

	public String getImageUrl(String title) throws ServiceException {
		try {
//			String url = this.imageNewsUrl.replace("{title}", title);
			String url = "https://serpapi.com/search.json?q=Apple&tbm=isch&ijn=0";
			ImageBody imageBody = restTemplate.getForObject(url, ImageBody.class);
			if (imageBody.getImages_results() == null)
				return "";
			else if (imageBody.getImages_results() == null)
				return "";
			else if (imageBody.getImages_results().isEmpty())
				return "";
			return imageBody.getImages_results().get(0).getOriginal();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public List<NewsInfo> getNews() {
		try {
			String newsBodyStr = restTemplate.getForObject(this.rssNewsHome, String.class);
			String json = getJsonFromXmlString(newsBodyStr);
			NewsBody body = objectMapper.readValue(json.getBytes(), NewsBody.class);
			return newsInfoService.getNewsInfoListFromRssNews(body);
		} catch (Exception e) {
			log.error(e.toString());
		}
		return new ArrayList<>();
	}

	public List<NewsInfo> searchNews(String q, String location) throws ServiceException {
		String url = this.rssNewsSearch;
		NewsBody body = new NewsBody();
		List<String> queryList = new ArrayList<>();
		if (q != null) {
			queryList.add("q=" + q);
		}
		if (location != null) {
			queryList.add("gl=" + location);
		}
		String query = String.join("&", queryList);
		url = url + "?" + query;
		log.info("Url: " + url);
		try {
			String newsBodyStr = restTemplate.getForObject(url, String.class);
			String json = getJsonFromXmlString(newsBodyStr);
			body = objectMapper.readValue(json.getBytes(), NewsBody.class);
			return newsInfoService.getNewsInfoListFromRssNews(body);

		} catch (RestClientException e) {
			log.error(e.toString());
			throw new RestClientException("External API call failed");
		} catch (Exception e) {
			log.error(e.toString());
			throw new ServiceException("Error");
		}
	}

	public List<NewsInfo> getNewsByTopic(String topic) throws ServiceException {
		try {
			String topicKey = NewsCategory.valueOf(topic.toUpperCase()).getCategoryValue();
			String url = this.rssNewsTopic.replace("{TOPIC_ID}", topicKey);
			String newsBodyStr = restTemplate.getForObject(url, String.class);
			String json = getJsonFromXmlString(newsBodyStr);
			NewsBody body = objectMapper.readValue(json.getBytes(), NewsBody.class);
			return newsInfoService.getNewsInfoListFromRssNews(body);
		} catch (IllegalArgumentException e) {
			log.error("Invalid topic name: " + topic.toUpperCase());
			throw new ServiceException("Invide topic");
		} catch (Exception e) {
			log.error(e.toString());
		}
		return null;
	}

}
