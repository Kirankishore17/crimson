package com.news.crimson.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.crimson.dao.NewsDao;
import com.news.crimson.dao.TopicNewsDao;
import com.news.crimson.entity.BookmarkedNews;
import com.news.crimson.exception.ServiceException;
import com.news.crimson.model.NewsInfo;
import com.news.crimson.model.TopicNewsInfo;
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

	@Value("${news.article.image}")
	private String imageNewsUrl;

	@Autowired
	private NewsInfoService newsInfoService;

	@Autowired
	private NewsDao newsDao;

	@Autowired
	private TopicNewsDao topicNewsDao;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private RestTemplate restTemplate;

	public List<NewsInfo> getNews() throws ServiceException {
		try {
			return newsDao.getNews();
		} catch (Exception e) {
			log.error(e.toString());
			throw new ServiceException(e.getMessage());
		}
	}

	public List<NewsInfo> searchNews(String q, String location) throws ServiceException {
		String url = this.rssNewsSearch;
		NewsBody body = new NewsBody();
		List<String> queryList = new ArrayList<>();
		try {

			if (q != null) {
				queryList.add("q=" + q);
			}
			if (location != null) {
				queryList.add("gl=" + location);
			}
			String query = String.join("&", queryList);
			url = url + "?" + query;
			log.info("Url: " + url);
			String newsBodyStr = restTemplate.getForObject(url, String.class);
			String json = newsInfoService.getJsonFromXmlString(newsBodyStr);
			body = objectMapper.readValue(json.getBytes(), NewsBody.class);
			return newsInfoService.getNewsInfoListFromRssNews(body, "search");

		} catch (RestClientException e) {
			log.error(e.toString());
			throw new RestClientException("External API call failed");
		} catch (Exception e) {
			log.error(e.toString());
			throw new ServiceException(e.getMessage());
		}
	}

	public List<TopicNewsInfo> getNewsByTopic(String topic) throws ServiceException {
		try {
			return topicNewsDao.getNewsByTopic(topic);

		} catch (IllegalArgumentException e) {
			log.error("Invalid topic name: " + topic.toUpperCase());
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			log.error(e.toString());
			throw new ServiceException(e.getMessage());
		}
	}

	public String bookmarkNews(Integer userId, NewsInfo newsInfo) {
		BookmarkedNews bookmark = new BookmarkedNews();
		bookmark.setUserId(userId.toString());
		bookmark.setSourceUrl(newsInfo.getSourceUrl());
		bookmark.setSourceName(newsInfo.getSourceName());
		bookmark.setPublishDate(newsInfo.getPublishDate());
		bookmark.setHeadlines(newsInfo.getHeadlines());
		bookmark.setCover(newsInfo.getCover());
		bookmark.setArticleUrl(newsInfo.getArticleUrl());
//		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
//		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
//		String savedDate = formatter.format(new Date());
//		bookmark.setSavedDate(savedDate.replace(".", ""));
		newsDao.bookmarkNews(bookmark);
		return "News Saved Successfully";
	}

	public String getBookmarkedNews(Integer userId) {
		return newsDao.getBookmarkedNews(userId);
	}

}
