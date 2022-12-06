package com.news.crimson.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.crimson.dao.AppLogDao;
import com.news.crimson.dao.NewsDao;
import com.news.crimson.dao.ShareNewsDao;
import com.news.crimson.dao.TopicNewsDao;
import com.news.crimson.dao.UserDao;
import com.news.crimson.entity.AppLog;
import com.news.crimson.entity.BookmarkedNews;
import com.news.crimson.entity.SharedNewsInfo;
import com.news.crimson.entity.User;
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
	private ShareNewsDao shareNewsDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AppLogDao appLogDao;

	public List<NewsInfo> getNews() throws ServiceException {
		AppLog appLog = new AppLog();
		appLog.setRequestBody("");
		appLog.setDescription("GET : /news/home | getNews | API called for Home page");
		appLog.setUserId("");
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		String timestamp = formatter.format(new Date());
		appLog.setTimestamp(timestamp);
		try {
			List<NewsInfo> res = newsDao.getNews();
	        Collections.shuffle(res);
			appLog.setType("INFO");
			appLog.setResponse(res.size() + " News Objects returned");
			appLog.setExceptionMessage("");
			appLogDao.saveLog(appLog);
			return res;
		} catch (Exception e) {
			log.error(e.toString());
			appLog.setType("ERROR");
			appLog.setExceptionMessage(
					e.getMessage().substring(0, 100 > e.getMessage().length() ? e.getMessage().length() : 100));
			appLog.setResponse("");
			appLogDao.saveLog(appLog);
			throw new ServiceException(e.getMessage());
		}
	}

	public List<NewsInfo> searchNews(String q, String location) throws ServiceException {
		String url = this.rssNewsSearch;
		NewsBody body = new NewsBody();
		List<String> queryList = new ArrayList<>();
		AppLog appLog = new AppLog();
		appLog.setDescription("GET : /news/search searchNews | API called for Search page");
		appLog.setUserId("");
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		String timestamp = formatter.format(new Date());
		appLog.setTimestamp(timestamp);
		try {
			if (q != null) {
				queryList.add("q=" + q);
			}
			if (location != null) {
				queryList.add("gl=" + location);
			}
			String query = String.join("&", queryList);
			url = url + "?" + query;
			log.info("Calling Search News Rss API: " + url);
			String newsBodyStr = restTemplate.getForObject(url, String.class);
			String json = newsInfoService.getJsonFromXmlString(newsBodyStr);
			body = objectMapper.readValue(json.getBytes(), NewsBody.class);
			List<NewsInfo> res = newsInfoService.getNewsInfoListFromRssNews(body, "search");
			appLog.setRequestBody(url);
			appLog.setType("INFO");
			appLog.setResponse(res.size() + " News Objects returned");
			appLog.setExceptionMessage("");
			appLogDao.saveLog(appLog);
			return res;
		} catch (RestClientException e) {
			log.error(e.toString());
			appLog.setType("ERROR");
			appLog.setExceptionMessage(
					e.getMessage().substring(0, 100 > e.getMessage().length() ? e.getMessage().length() : 100));
			appLog.setResponse("");
			appLogDao.saveLog(appLog);
			throw new RestClientException("External API call failed");
		} catch (Exception e) {
			log.error(e.toString());
			throw new ServiceException(e.getMessage());
		}
	}

	public List<TopicNewsInfo> getNewsByTopic(String topic) throws ServiceException {
		AppLog appLog = new AppLog();
		appLog.setDescription("GET : /news/topic getNewsByTopic | API called for topic by news - " + topic);
		appLog.setUserId("");
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		String timestamp = formatter.format(new Date());
		appLog.setTimestamp(timestamp);
		try {
			List<TopicNewsInfo> res = topicNewsDao.getNewsByTopic(topic);
			appLog.setRequestBody("");
			appLog.setType("INFO");
			appLog.setResponse(res.size() + " News Objects returned");
			appLog.setExceptionMessage("");
			appLogDao.saveLog(appLog);
			return res;
		} catch (IllegalArgumentException e) {
			log.error("Invalid topic name: " + topic.toUpperCase());
			appLog.setType("ERROR");
			appLog.setExceptionMessage(
					e.getMessage().substring(0, 100 > e.getMessage().length() ? e.getMessage().length() : 100));
			appLog.setResponse("");
			appLogDao.saveLog(appLog);
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			log.error(e.toString());
			appLog.setType("ERROR");
			appLog.setExceptionMessage(
					e.getMessage().substring(0, 100 > e.getMessage().length() ? e.getMessage().length() : 100));
			appLog.setResponse("");
			appLogDao.saveLog(appLog);
			throw new ServiceException(e.getMessage());
		}
	}

	public String bookmarkNews(Integer userId, NewsInfo newsInfo) {
		AppLog appLog = new AppLog();
		appLog.setDescription("POST : /news/readlater bookmarkNews | API called to add news to read later");
		appLog.setUserId(userId.toString());
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		String timestamp = formatter.format(new Date());
		appLog.setTimestamp(timestamp);
		try {
			BookmarkedNews bookmark = new BookmarkedNews();
			bookmark.setUserId(userId.toString());
			bookmark.setSourceUrl(newsInfo.getSourceUrl());
			bookmark.setSourceName(newsInfo.getSourceName());
			bookmark.setPublishDate(newsInfo.getPublishDate());
			bookmark.setHeadlines(newsInfo.getHeadlines());
			bookmark.setCover(newsInfo.getCover());
			bookmark.setArticleUrl(newsInfo.getArticleUrl());
			newsDao.bookmarkNews(bookmark);
			appLog.setType("INFO");
			appLog.setResponse("News Bookmarked");
			appLog.setExceptionMessage("");
			appLogDao.saveLog(appLog);
			return "News Saved Successfully";
		} catch (Exception e) {
			log.error(e.getMessage());
			appLog.setType("ERROR");
			appLog.setExceptionMessage(
					e.getMessage().substring(0, 100 > e.getMessage().length() ? e.getMessage().length() : 100));
			appLog.setResponse("");
			appLogDao.saveLog(appLog);
			return "Unable to bookmark";
		}
	}

	public String getBookmarkedNews(Integer userId) {
		return newsDao.getBookmarkedNews(userId);
	}

	public String shareNews(SharedNewsInfo sharedNewsInfo) {
		boolean alreadyShared = shareNewsDao.checkExists(sharedNewsInfo);
		if (!alreadyShared) {
			SharedNewsInfo info = shareNewsDao.shareNews(sharedNewsInfo);
			if (info.getId() != null)
				return "News Shared to Feed";
			else
				return "Share news Failed";
		} else {
			return "News Already Shared";
		}
	}

	public List<SharedNewsInfo> getFeed(Integer userId) throws ServiceException {
		AppLog appLog = new AppLog();
		appLog.setDescription("GET : /news/feed getFeed | API called to fetch news shared by friends and followers");
		appLog.setUserId(userId.toString());
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		String timestamp = formatter.format(new Date());
		appLog.setTimestamp(timestamp);
		try {
			User user = userDao.findUserById(userId);
			if (user == null)
				throw new ServiceException("Invalid userId");
			List<Integer> ids = new ArrayList<>();
			user.getFriends().forEach(x -> ids.add(x.getFriendId()));
			user.getFollowers().forEach(x -> ids.add(x.getFollowerId()));
			ids.add(user.getId());
			List<SharedNewsInfo> res = shareNewsDao.getFeed(ids);
			appLog.setType("INFO");
			appLog.setResponse(res.size() + " News Objects returned");
			appLog.setExceptionMessage("");
			appLogDao.saveLog(appLog);
			return res;
		} catch (Exception e) {
			log.error(e.getMessage());
			appLog.setType("ERROR");
			appLog.setExceptionMessage(
					e.getMessage().substring(0, 100 > e.getMessage().length() ? e.getMessage().length() : 100));
			appLog.setResponse("");
			appLogDao.saveLog(appLog);
			return new ArrayList<>();
		}
	}
}
