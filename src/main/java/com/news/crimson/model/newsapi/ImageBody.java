package com.news.crimson.model.newsapi;

import java.util.List;

import lombok.Data;

@Data
public class ImageBody {

	private Object _type;
	private Object instrumentation;
	private Object readLink;
	private Object webSearchUrl;
	private Object queryContext;
	private Object totalEstimatedMatches;
	private Object nextOffset;
	private Object currentOffset;
	private Object pivotSuggestions;
	private List<ImageItem> value;

}
