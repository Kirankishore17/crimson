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

	public List<User> findAll() {
		List<User> userList = repo.findAll();
		return userList;
	}
	
	public User findUser(User user) {
		List<User> userList = repo.getUserByEmailAndSource(user.getEmail(), user.getLoginSource());
		if(userList.size() == 1) {
			return userList.get(0);
		} else return null;
		
	}

	public User addUser(User user) {
		return repo.save(user);
	}

	public void deleteById(int id) {
		repo.deleteById(id);
	}

	public User updateProfile(User user) {
		List<Integer> userID = repo.getUserIDByEmailAndSource(user.getEmail(), user.getLoginSource());
		
		if (userID.isEmpty()) {
			return repo.save(user);
		} else if (userID.size() == 1) {
			user.setId(userID.get(0));
			return repo.save(user);
			
			} else
				return null;
		
	}

}
