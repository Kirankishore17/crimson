package com.news.crimson.dao;

import java.util.HashMap;
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
	
	public Boolean getUser(String email, String loginSource) {
		List<User> user = repo.getUserByEmailAndSource(email, loginSource);
		if(user.size() == 1) return true;
		else return false;
	}

	public User findUserById(Integer userId) {
		 
		 List<User> userList = repo.getUserById(userId);;
			if(userList.size() == 1) {
				return userList.get(0);
			} else return null;
	}
	
	public Integer numberOfUsers() {
		return repo.countTotalNumberOfUsers();
	}
	
	public HashMap<String,Integer> categoryCount(){
		
		HashMap<String,Integer> categoryListCount = new HashMap<String,Integer>();
		categoryListCount.put("WORLD", repo.countTotalNumberOfUsersForCategoryWorld());
		categoryListCount.put("LOCAL", repo.countTotalNumberOfUsersForCategoryLocal());
		categoryListCount.put("BUSINESS", repo.countTotalNumberOfUsersForCategoryBusiness());
		categoryListCount.put("TECHNOLOGY", repo.countTotalNumberOfUsersForCategoryTechnology());
		categoryListCount.put("ENTERTAINMENT", repo.countTotalNumberOfUsersForCategoryEntertainment());
		categoryListCount.put("SPORTS", repo.countTotalNumberOfUsersForCategorySports());
		categoryListCount.put("SCIENCE", repo.countTotalNumberOfUsersForCategoryScience());
		categoryListCount.put("HEALTH", repo.countTotalNumberOfUsersForCategoryHealth());
		return categoryListCount;
		
	}

}
