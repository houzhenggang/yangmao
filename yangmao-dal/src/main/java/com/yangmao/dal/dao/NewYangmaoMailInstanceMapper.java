package com.yangmao.dal.dao;

import com.yangmao.dal.dataobj.YangmaoMailInstance;
import com.yangmao.dal.dataobj.YangmaoMailInstanceExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NewYangmaoMailInstanceMapper {

    /**
     * 查询生成邮件实体列表
     * @param map 条件
     * @return
     */
    public List<YangmaoMailInstance> selectInstanceEmailForPage(Map<String,Object> map);

    /**
     * 查询有效的生成邮件实体总数
     * @param map 条件
     * @return
     */
    public Integer countsInstanceEmailForPage(Map<String,Object> map);
}