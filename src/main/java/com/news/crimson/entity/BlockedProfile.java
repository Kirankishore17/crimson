package com.news.crimson.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="BlockedProfile")
public class BlockedProfile {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "BLOCKED_ID")
	private Integer id;
	
	@Column(name = "USER_ID")
	private Integer userId;
	
	@Column(name = "BLOCKED_PROFILE_NAME")
	private String blockedProfileName;

}
