package com.news.crimson.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TopicNewsInfo")
public class TopicNewsInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	private String headlines;
	@Column(name="source_url", length = 700)
	private String sourceUrl;
	private String sourceName;
	@Column(name = "article_url", length = 700)
	private String articleUrl;
	private String publishDate;
	@Column(name="cover", length = 700)
	private String cover;
	private String topic;

}
