package com.news.crimson.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class FriendProfile {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	public Integer id;	
	
	@Column(name = "USER_ID")
	private Integer userId;
//	private Integer friendId;
	
	@Column(name = "FRIEND_PROFILE_NAME")
	private String friendProfileName;
	
	@Column(name = "FRIEND_STATUS")
	private String friendStatus;
	
}
