package com.yangmao.dal.dao;

import com.yangmao.dal.dataobj.YangmaoTemplateSection;
import com.yangmao.dal.dataobj.YangmaoTemplateSectionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YangmaoTemplateSectionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_template_section
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    int countByExample(YangmaoTemplateSectionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_template_section
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    int deleteByExample(YangmaoTemplateSectionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_template_section
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    int deleteByPrimaryKey(Long sectionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_template_section
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    int insert(YangmaoTemplateSection record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_template_section
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    int insertSelective(YangmaoTemplateSection record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_template_section
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    List<YangmaoTemplateSection> selectByExample(YangmaoTemplateSectionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_template_section
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    YangmaoTemplateSection selectByPrimaryKey(Long sectionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_template_section
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    int updateByExampleSelective(@Param("record") YangmaoTemplateSection record, @Param("example") YangmaoTemplateSectionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_template_section
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    int updateByExample(@Param("record") YangmaoTemplateSection record, @Param("example") YangmaoTemplateSectionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_template_section
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    int updateByPrimaryKeySelective(YangmaoTemplateSection record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table yangmao_template_section
     *
     * @mbggenerated Mon Aug 29 14:47:22 CST 2016
     */
    int updateByPrimaryKey(YangmaoTemplateSection record);
}