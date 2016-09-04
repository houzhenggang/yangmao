package com.yangmao.dal.dao;

import com.yangmao.dal.dataobj.YangmaoFavoritesItem;
import com.yangmao.dal.dataobj.YangmaoFavoritesItemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 新商品dao
 */
public interface NewYangmaoFavoritesItemMapper {
    /**
     * 通过创建时间以及品类组id查询
     * @param map 条件
     * @return
     */
    public List<YangmaoFavoritesItem> selectFavoritesItemsListByCreateTimeAndId(Map<String,Object> map);

    /**
     * 通过产品id查询产品明细列表
     * @param map 条件
     * @return
     */
    public List<YangmaoFavoritesItem> selectFavoritesItemsListByItemsId(Map<String,Object> map);
}