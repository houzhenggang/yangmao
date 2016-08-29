package com.yangmao.dal.dao;

import com.yangmao.dal.dataobj.YangmaoMailInstance;
import com.yangmao.dal.dataobj.YangmaoMailInstanceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YangmaoMailInstanceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_mail_instance
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    int countByExample(YangmaoMailInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_mail_instance
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    int deleteByExample(YangmaoMailInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_mail_instance
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    int deleteByPrimaryKey(Long mailInstanceId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_mail_instance
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    int insert(YangmaoMailInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_mail_instance
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    int insertSelective(YangmaoMailInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_mail_instance
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    List<YangmaoMailInstance> selectByExampleWithBLOBs(YangmaoMailInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_mail_instance
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    List<YangmaoMailInstance> selectByExample(YangmaoMailInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_mail_instance
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    YangmaoMailInstance selectByPrimaryKey(Long mailInstanceId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_mail_instance
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    int updateByExampleSelective(@Param("record") YangmaoMailInstance record, @Param("example") YangmaoMailInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_mail_instance
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    int updateByExampleWithBLOBs(@Param("record") YangmaoMailInstance record, @Param("example") YangmaoMailInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_mail_instance
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    int updateByExample(@Param("record") YangmaoMailInstance record, @Param("example") YangmaoMailInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_mail_instance
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    int updateByPrimaryKeySelective(YangmaoMailInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_mail_instance
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    int updateByPrimaryKeyWithBLOBs(YangmaoMailInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_mail_instance
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    int updateByPrimaryKey(YangmaoMailInstance record);
}