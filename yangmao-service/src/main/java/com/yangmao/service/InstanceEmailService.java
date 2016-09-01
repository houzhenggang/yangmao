package com.yangmao.service;

import com.yangmao.model.admin.dto.EmailInstanceTemplateModel;

/**
 * Created by liyongfeng on 16/9/1.
 */
public interface InstanceEmailService {

    /**
     * 通过模板id查询模板详尽信息
     * @param templateId 模板id
     * @return
     */
    public EmailInstanceTemplateModel selectTemplate(long templateId) throws Exception;


}
