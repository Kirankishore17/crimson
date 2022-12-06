package com.news.crimson.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
//@Table(name = "AppLog ")
public class AppLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String timestamp;
	private String userId;
	@Column(name="requestBody", length = 700)
	private String requestBody;
	@Column(name="response", length = 700)
	private String response;
	private String description;
	private String exceptionMessage;
	private String type;
}
