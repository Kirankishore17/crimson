package com.news.crimson.dao;

import java.util.ArrayList;
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
	
	public User getUserbyEmailAndLoginSource(String email, String loginSource) {
		List<User> user = repo.getUserByEmailAndSource(email, loginSource);
		if(user.size() == 1) return user.get(0);
		else return new User();
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
	
	public List<HashMap<String,String>> categoryCount(){
		
		List<HashMap<String,String>> categoryListCount = new ArrayList<HashMap<String,String>>();
		HashMap<String,String> map1 = new HashMap<>();
		map1.put("name","WORLD");
		map1.put("count",repo.countTotalNumberOfUsersForCategoryWorld().toString());
		categoryListCount.add(map1);
		HashMap<String,String> map2 = new HashMap<>();
		map2.put("name","BUSINESS");
		map2.put("count",repo.countTotalNumberOfUsersForCategoryBusiness().toString());
		categoryListCount.add(map2);
		HashMap<String,String> map3 = new HashMap<>();
		map3.put("name","TECHNOLOGY");
		map3.put("count",repo.countTotalNumberOfUsersForCategoryTechnology().toString());
		categoryListCount.add(map3);
		HashMap<String,String> map4 = new HashMap<>();
		map4.put("name","ENTERTAINMENT");
		map4.put("count",repo.countTotalNumberOfUsersForCategoryEntertainment().toString());
		categoryListCount.add(map4);
		HashMap<String,String> map5 = new HashMap<>();
		map5.put("name","SPORTS");
		map5.put("count",repo.countTotalNumberOfUsersForCategorySports().toString());
		categoryListCount.add(map5);
		HashMap<String,String> map6 = new HashMap<>();
		map6.put("name","SCIENCE");
		map6.put("count",repo.countTotalNumberOfUsersForCategoryScience().toString());
		categoryListCount.add(map6);
		HashMap<String,String> map7 = new HashMap<>();
		map7.put("name","HEALTH");
		map7.put("count",repo.countTotalNumberOfUsersForCategoryHealth().toString());
		categoryListCount.add(map7);
		
		return categoryListCount;
		
	}

	public List<User> getProfileByLocation(String location) {
		return repo.getProfileByLocation(location);
	}

	public List<User> getProfileByNewsCategory(String category) {
		return repo.getProfileByNewsCategory(category);
	}

}
