package com.news.crimson.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.news.crimson.dao.AppLogDao;
import com.news.crimson.dao.UserDao;
import com.news.crimson.entity.AppLog;
import com.news.crimson.entity.User;

@Service
public class AdminService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private AppLogDao appLogDao;

	public List<User> getAllProfiles() {
		return userDao.findAll();
	}

	public Integer getnumberOfUsers() {
		return userDao.numberOfUsers();
	}

	public List<HashMap<String,String>> getCategoryCount(){
		return userDao.categoryCount();
	}

//	public ByteArrayInputStream downloadApplicationLogs() {
//		try {
//			List<AppLog> list = appLogDao.getApplicationLogs();
//			list.stream().
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		    ObjectOutputStream oos = new ObjectOutputStream(baos);
//			 for (AppLog i : list) {
//				    oos.writeObject(i);
//			 }
//			 oos.flush();
//			 oos.close();
//			 return  new ByteArrayInputStream(baos.toByteArray());			
//		} catch (Exception e) {
//			System.err.println("Fecht app logs failed");
//		}
//	}

	public List<AppLog> getApplicationLogs() {
		return appLogDao.getApplicationLogs();
	}

}