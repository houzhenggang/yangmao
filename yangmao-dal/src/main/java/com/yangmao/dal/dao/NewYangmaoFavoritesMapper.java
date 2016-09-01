package com.yangmao.dal.dao;

import com.yangmao.dal.dataobj.YangmaoFavorites;
import com.yangmao.dal.dataobj.YangmaoFavoritesExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 自定义品类组dao
 */
public interface NewYangmaoFavoritesMapper {

    /**
     * 获取品类组列表
     * @return
     */
    public List<YangmaoFavorites> selectFavoritesList();

}