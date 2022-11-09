package com.news.crimson.model;

import lombok.Data;

@Data
public class ResponseInfo {

	private String message;

	public ResponseInfo(String message) {
		super();
		this.message = message;
	}
}
