package com.yangmao.model.Response;

import com.alibaba.fastjson.annotation.JSONField;

public class TbkUatmFavoritesGetResponse {

	public FavoritesGetResponse getFavoritesGetResponse() {
		return favoritesGetResponse;
	}

	public void setFavoritesGetResponse(FavoritesGetResponse favoritesGetResponse) {
		this.favoritesGetResponse = favoritesGetResponse;
	}

	@JSONField(name = "tbk_uatm_favorites_get_response")
	private FavoritesGetResponse favoritesGetResponse;
	
}
