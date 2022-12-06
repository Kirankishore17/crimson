package com.news.crimson.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.news.crimson.entity.AppLog;
import com.news.crimson.repo.AppLogRepo;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class AppLogDao {

	@Autowired
	private AppLogRepo appLogRepo;

	public void saveLog(AppLog appLog) {
		try {
			appLogRepo.save(appLog);
		} catch(Exception e) {
			log.error("Save Log Failed: " + e.getMessage());
		}
	}

	public List<AppLog> getApplicationLogs() {
		return appLogRepo.getApplicationLogs();
	}
	
	
}
