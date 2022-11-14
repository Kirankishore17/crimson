package com.news.crimson.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;  

@Data
@Entity
@Table(name="User")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	public Integer id;	

	@Column(name = "FIRST_NAME")
	public String firstName;
	
	@Column(name = "LAST_NAME")
	public String lastName;
	
	@Column(name = "EMAIL")
	public String email;
	
	@Column(name = "PROFILE_PIC", length = 500)
	public String profilePic;
	
	@Column(name = "LOGIN_SOURCE")
	public String loginSource;
	
	@Column(name = "LOCATION")
	public String location;
	
	@Column(name = "FAV_CATEGORY")
	public String favCategory;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID")
	public List<FriendProfile> friends;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID")
	public List<FollowerProfile> followers;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID")
	public List<BlockedProfile> blocked;	

}
