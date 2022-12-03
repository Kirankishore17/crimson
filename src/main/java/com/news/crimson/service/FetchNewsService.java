package com.news.crimson.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.crimson.dao.NewsDao;
import com.news.crimson.dao.TopicNewsDao;
import com.news.crimson.exception.ServiceException;
import com.news.crimson.model.NewsCategory;
import com.news.crimson.model.NewsInfo;
import com.news.crimson.model.TopicNewsInfo;
import com.news.crimson.model.newsapi.NewsBody;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FetchNewsService {

	@Value("${rss.news.home}")
	private String rssNewsHome;

	@Value("${rss.news.topic}")
	private String rssNewsTopic;

	@Value("${news.article.image}")
	private String imageNewsUrl;

	@Autowired
	private NewsInfoService newsInfoService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private NewsDao newsDao;

	@Autowired
	private TopicNewsDao topicNewsDao;

	public List<NewsInfo> fetchNews() throws ServiceException {
		String json;
		try {
			String newsBodyStr = restTemplate.getForObject(this.rssNewsHome, String.class);
			json = newsInfoService.getJsonFromXmlString(newsBodyStr);
			NewsBody body = objectMapper.readValue(json.getBytes(), NewsBody.class);
			List<NewsInfo> list = newsInfoService.getNewsInfoListFromRssNews(body, "fetchNews");
			return newsDao.saveNews(list);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		} catch (ServiceException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}

	public String fetchNewsByTopic() throws ServiceException {
		String json;
		try {
			for (NewsCategory topic : NewsCategory.values()) {
				String topicKey = topic.getCategoryValue();
				String url = this.rssNewsTopic.replace("{TOPIC_ID}", topicKey);
				String newsBodyStr = restTemplate.getForObject(url, String.class);
				json = newsInfoService.getJsonFromXmlString(newsBodyStr);
				NewsBody body = objectMapper.readValue(json.getBytes(), NewsBody.class);
				List<NewsInfo> list = newsInfoService.getNewsInfoListFromRssNews(body, "topic");
				List<TopicNewsInfo> topicList = getTopicNewsListFromNewsInfoList(list, topic.toString().toUpperCase());
				List<TopicNewsInfo> response = topicNewsDao.saveTopicNews(topicList);
				log.info(response.size() + " entries added to category:" + topic.toString());
			}

		} catch (IOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		} catch (ServiceException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return "success";
	}

	private List<TopicNewsInfo> getTopicNewsListFromNewsInfoList(List<NewsInfo> list, String topic) {
		List<TopicNewsInfo> topicList = new ArrayList<>();
		for(NewsInfo i:list) {
			TopicNewsInfo item = new TopicNewsInfo();
			item.setArticleUrl(i.getArticleUrl());
			item.setCover(i.getCover());
			item.setHeadlines(i.getHeadlines());
			item.setPublishDate(i.getPublishDate());
			item.setSourceName(i.getSourceName());
			item.setSourceUrl(i.getSourceUrl());
			item.setTopic(topic);
			topicList.add(item);
		}
		return topicList;
	}

}
