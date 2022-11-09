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
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "blocked_id")
	private Integer id;
	private Integer userId;
	private String blockedProfileName;


}
