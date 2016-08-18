package com.yangmao.model.Response;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class FavoritesGetResult {

	
	public List<TbkFavorite> getTbkFavorites() {
		return tbkFavorites;
	}

	public void setTbkFavorites(List<TbkFavorite> tbkFavorites) {
		this.tbkFavorites = tbkFavorites;
	}

	@JSONField(name = "tbk_favorites")
	private List<TbkFavorite> tbkFavorites;

	
}
