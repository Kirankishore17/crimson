package com.news.crimson.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.news.crimson.entity.AppLog;

public interface AppLogRepo extends JpaRepository<AppLog, Integer>{

	@Query(value = "SELECT * FROM app_log u order by id desc limit 50", nativeQuery = true)
	public List<AppLog> getApplicationLogs();

}
