package com.yangmao.model.Response;
import com.alibaba.fastjson.annotation.JSONField;

public class TbkFavorite {

	
	public Long getFavoritesId() {
		return favoritesId;
	}

	public void setFavoritesId(Long favoritesId) {
		this.favoritesId = favoritesId;
	}

	public String getFavoritesTitle() {
		return favoritesTitle;
	}

	public void setFavoritesTitle(String favoritesTitle) {
		this.favoritesTitle = favoritesTitle;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@JSONField(name = "favorites_id")
	private Long favoritesId;
	
	@JSONField(name = "favorites_title")
	private String favoritesTitle;
	
	@JSONField(name = "type")
	private Integer type;

	
}
