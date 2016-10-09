package com.yangmao.dal.dao;

import com.yangmao.dal.dataobj.YangmaoUser;
import com.yangmao.dal.dataobj.YangmaoUserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户dao
 */
public interface NewYangmaoUserMapper {

    /**
     * 查询用户列表
     * @param map 条件
     * @return
     */
    List<YangmaoUser> selectUserListByPage(Map<String,Object> map);

    /**
     * 查询用户数
     * @param map 条件
     * @return
     */
    int countUserListByPage(Map<String,Object> map);

}