package com.news.crimson.model;

import java.util.List;

import lombok.Data;

@Data
public class ProfileInfo {

	public Integer id;	
	public String firstName;
	public String lastName;
	public String email;
	public String phoneNo;
	public String location;
	public List<String> favCategory;

}
