package com.news.crimson.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.news.crimson.entity.User;
import com.news.crimson.repo.UserRepo;

@Repository("UserRepo")
public class UserDao {

	@Autowired
	UserRepo repo;
	
	public List<User> findAll(){
		List<User> userList = repo.findAll();
		return 	userList;
	}
	
	public User addUser(User user) {
		return repo.save(user);
	}

	public void deleteById(int id) {
		repo.deleteById(id);
	}



}
