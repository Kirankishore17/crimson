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
@Table(name="BookmarkedNews")
public class BookmarkedNews {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String userId;
	private String headlines;
	@Column(name="source_url", length = 700)
	private String sourceUrl;
	private String sourceName;
	@Column(name = "article_url", length = 700)
	private String articleUrl;
	private String publishDate;
	@Column(name="cover", length = 700)
	private String cover;

}
