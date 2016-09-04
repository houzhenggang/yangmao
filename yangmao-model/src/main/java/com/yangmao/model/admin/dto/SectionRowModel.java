package com.yangmao.model.admin.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 选品类型行
 * Created by liyongfeng on 16/9/2.
 */
public class SectionRowModel {

    /**
     * 产品列表
     */
    private List<FavoritesItemsModel> favoritesItemsModels = new ArrayList<>();

    public List<FavoritesItemsModel> getFavoritesItemsModels() {
        return favoritesItemsModels;
    }

    public void setFavoritesItemsModels(List<FavoritesItemsModel> favoritesItemsModels) {
        this.favoritesItemsModels = favoritesItemsModels;
    }
}
