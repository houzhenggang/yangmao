package com.yangmao.dal.dao;

import com.yangmao.dal.dataobj.YangmaoUser;
import com.yangmao.dal.dataobj.YangmaoUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YangmaoUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_user
     *
     * @mbggenerated Thu Aug 11 17:57:34 CST 2016
     */
    int countByExample(YangmaoUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_user
     *
     * @mbggenerated Thu Aug 11 17:57:34 CST 2016
     */
    int deleteByExample(YangmaoUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_user
     *
     * @mbggenerated Thu Aug 11 17:57:34 CST 2016
     */
    int deleteByPrimaryKey(Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_user
     *
     * @mbggenerated Thu Aug 11 17:57:34 CST 2016
     */
    int insert(YangmaoUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_user
     *
     * @mbggenerated Thu Aug 11 17:57:34 CST 2016
     */
    int insertSelective(YangmaoUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_user
     *
     * @mbggenerated Thu Aug 11 17:57:34 CST 2016
     */
    List<YangmaoUser> selectByExample(YangmaoUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_user
     *
     * @mbggenerated Thu Aug 11 17:57:34 CST 2016
     */
    YangmaoUser selectByPrimaryKey(Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_user
     *
     * @mbggenerated Thu Aug 11 17:57:34 CST 2016
     */
    int updateByExampleSelective(@Param("record") YangmaoUser record, @Param("example") YangmaoUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_user
     *
     * @mbggenerated Thu Aug 11 17:57:34 CST 2016
     */
    int updateByExample(@Param("record") YangmaoUser record, @Param("example") YangmaoUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_user
     *
     * @mbggenerated Thu Aug 11 17:57:34 CST 2016
     */
    int updateByPrimaryKeySelective(YangmaoUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_user
     *
     * @mbggenerated Thu Aug 11 17:57:34 CST 2016
     */
    int updateByPrimaryKey(YangmaoUser record);
}