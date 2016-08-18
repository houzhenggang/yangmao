package com.yangmao.model.Response;

import com.alibaba.fastjson.annotation.JSONField;

public class FavoritesGetResponse {


	public FavoritesGetResult getResults() {
		return results;
	}

	public void setResults(FavoritesGetResult results) {
		this.results = results;
	}

	public Integer getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(Integer totalResults) {
		this.totalResults = totalResults;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	private FavoritesGetResult results;
	
	@JSONField(name = "total_results")
	private Integer totalResults;
	
	@JSONField(name = "request_id")
	private String requestId;
	
}
