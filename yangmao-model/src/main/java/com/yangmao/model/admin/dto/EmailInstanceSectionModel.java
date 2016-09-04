package com.yangmao.model.admin.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyongfeng on 16/9/1.
 */
public class EmailInstanceSectionModel {

    /**
     * 品类id
     */
    private Long favoritesId;

    /**
     * 邮件组id
     */
    private Long sectionId;

    /**
     * 品类名称
     */
    private String section;

    /**
     * 品类数量
     */
    private Integer sectionAmount;

    /**
     * 产品列表
     */
    private List<FavoritesItemsModel> favoritesItemsModels = new ArrayList<>();

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public List<FavoritesItemsModel> getFavoritesItemsModels() {
        return favoritesItemsModels;
    }

    public void setFavoritesItemsModels(List<FavoritesItemsModel> favoritesItemsModels) {
        this.favoritesItemsModels = favoritesItemsModels;
    }

    public Long getFavoritesId() {
        return favoritesId;
    }

    public void setFavoritesId(Long favoritesId) {
        this.favoritesId = favoritesId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getSectionAmount() {
        return sectionAmount;
    }

    public void setSectionAmount(Integer sectionAmount) {
        this.sectionAmount = sectionAmount;
    }
}
