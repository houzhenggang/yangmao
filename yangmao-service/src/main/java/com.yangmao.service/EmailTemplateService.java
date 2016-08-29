package com.yangmao.service;

import com.yangmao.dal.dataobj.YangmaoMailTemplate;
import com.yangmao.model.admin.dto.MailTemplateModel;
import com.yangmao.model.common.Page;

import java.util.List;

/**
 * Created by liyongfeng on 16/8/24.
 */
public interface EmailTemplateService {

    /**
     * 保存邮件模板
     * @param template 邮件模板实体
     * @return
     */
    int insertEmailTemplate(YangmaoMailTemplate template) throws Exception;

    /**
     * 通过id查询邮件模板
     * @param templateId 邮件模板id
     * @return
     */
    YangmaoMailTemplate selectEmailTemplateById(long templateId) throws Exception;

    /**
     * 修改模板保存至数据库
     * @param template 邮件模板实体
     * @return
     */
    int updateEmailTemplate(YangmaoMailTemplate template) throws Exception;

    /**
     * 通过名字查询模板
     * @param page 分页
     * @param name 模板名称
     * @return
     */
    List<MailTemplateModel> getTemplateListForPage(Page page,String name) throws Exception;

    /**
     * 删除废弃的模板
     * @param templateId 模板id
     * @return
     */
    int deleteEmailTemplate(long templateId) throws Exception;
}
