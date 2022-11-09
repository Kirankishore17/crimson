package com.news.crimson.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class FollowerProfile {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	public Integer id;	
	
	@Column(name = "USER_ID")
	private Integer userId;
//	private Integer followerId;
	@Column(name = "FOLLOWER_PROFILE_NAME")
	private String followerProfileName;


}
