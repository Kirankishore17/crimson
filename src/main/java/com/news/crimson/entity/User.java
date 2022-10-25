package com.news.crimson.entity;

import java.util.List;

import lombok.Data;

@Data
public class User {

	public Integer id;	
	public String firstName;
	public String lastName;
	public String email;
	public String phoneNo;
	public String location;
	public List<String> favCategory;
	public List<Object> friends;
	public List<Object> followers;
	public List<Object> blocked;	

}
