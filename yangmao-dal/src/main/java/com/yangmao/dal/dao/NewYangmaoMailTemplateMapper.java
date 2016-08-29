package com.yangmao.dal.dao;

import com.yangmao.dal.dataobj.YangmaoMailTemplate;
import com.yangmao.dal.dataobj.YangmaoMailTemplateExample;
import com.yangmao.model.admin.dto.MailTemplateModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * @author liyongfeng
 */
public interface NewYangmaoMailTemplateMapper {

    /**
     * 获取邮件模板列表
     * @param map 条件
     * @return
     */
    public List<MailTemplateModel> selectEmailTemplateListForPage(Map<String,Object> map);

    /**
     * 获取邮件可用模板总数
     * @param map 条件
     * @return
     */
    public Integer countEmailTemplateListForPage(Map<String,Object> map);
}