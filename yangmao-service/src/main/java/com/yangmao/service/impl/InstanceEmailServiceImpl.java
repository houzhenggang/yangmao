package com.yangmao.service.impl;

import com.yangmao.dal.dao.YangmaoMailTemplateMapper;
import com.yangmao.dal.dao.YangmaoTemplateSectionMapper;
import com.yangmao.dal.dataobj.YangmaoMailTemplate;
import com.yangmao.dal.dataobj.YangmaoMailTemplateExample;
import com.yangmao.dal.dataobj.YangmaoTemplateSection;
import com.yangmao.dal.dataobj.YangmaoTemplateSectionExample;
import com.yangmao.model.admin.dto.EmailInstanceSectionModel;
import com.yangmao.model.admin.dto.EmailInstanceTemplateModel;
import com.yangmao.model.common.Constants;
import com.yangmao.model.common.Messages;
import com.yangmao.model.exception.TemplateException;
import com.yangmao.service.InstanceEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板生成邮件服务
 * Created by liyongfeng on 16/9/1.
 */
@Service
public class InstanceEmailServiceImpl implements InstanceEmailService {

    /**
     * 邮件模板dao
     */
    @Autowired
    private YangmaoMailTemplateMapper templateMapper;

    /**
     * 品类模板dao
     */
    @Autowired
    private YangmaoTemplateSectionMapper sectionMapper;
    /**
     *
     * @param templateId 模板id
     * @return
     */
    @Override
    public EmailInstanceTemplateModel selectTemplate(long templateId) throws Exception{
        EmailInstanceTemplateModel emailInstanceTemplateModel = new EmailInstanceTemplateModel();
        List<EmailInstanceSectionModel> sectionModels = new ArrayList<>();
        if(templateId == 0){
            throw new TemplateException(Messages.TEMPLATE_ID_IS_NULL_CODE,Messages.TEMPLATE_ID_IS_NULL_MSG);
        }

        YangmaoMailTemplateExample example = new YangmaoMailTemplateExample();
        example.createCriteria().andStatusEqualTo(Constants.TEMPLATE_STATUS_NORMAL).andTemplateIdEqualTo(templateId);
        List<YangmaoMailTemplate> templates =  templateMapper.selectByExample(example);
        if(templates.isEmpty()){
            throw new TemplateException(Messages.TEMPLATE_NOT_FIND_CODE,Messages.TEMPLATE_NOT_FIND_MSG);
        }

        YangmaoMailTemplate template = templates.get(0);
        emailInstanceTemplateModel.setContent(template.getContent());
        emailInstanceTemplateModel.setTemplateId(templateId);
        emailInstanceTemplateModel.setTitle(template.getTitle());

        YangmaoTemplateSectionExample sectionExample = new YangmaoTemplateSectionExample();
        sectionExample.createCriteria().andTemplateIdEqualTo(templateId);
        List<YangmaoTemplateSection> sections = sectionMapper.selectByExample(sectionExample);

        for(YangmaoTemplateSection section : sections){
            EmailInstanceSectionModel sectionModel = new EmailInstanceSectionModel();
            sectionModel.setSection(section.getSection());
            sectionModel.setSectionAmount(section.getSectionAmount());
            sectionModel.setSectionId(section.getSectionId());
            sectionModels.add(sectionModel);
        }
        emailInstanceTemplateModel.setSectionModelList(sectionModels);

        return emailInstanceTemplateModel;
    }
}
