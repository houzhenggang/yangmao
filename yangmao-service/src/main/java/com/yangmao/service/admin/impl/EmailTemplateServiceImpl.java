package com.yangmao.service.admin.impl;

import com.yangmao.dal.dao.*;
import com.yangmao.dal.dataobj.YangmaoFavorites;
import com.yangmao.dal.dataobj.YangmaoMailTemplate;
import com.yangmao.dal.dataobj.YangmaoTemplateSection;
import com.yangmao.dal.dataobj.YangmaoTemplateSectionExample;
import com.yangmao.model.admin.dto.MailTemplateModel;
import com.yangmao.model.common.Constants;
import com.yangmao.model.common.Page;
import com.yangmao.service.admin.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 邮件模板服务
 * Created by liyongfeng on 16/8/24.
 */
@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

    /**
     * 邮件模板dao
     */
    @Autowired
    private YangmaoMailTemplateMapper templateMapper;

    /**
     * 新邮件模板dao
     */
    @Autowired
    private NewYangmaoMailTemplateMapper newYangmaoMailTemplateMapper;

    /**
     * 品类dao
     */
    @Autowired
    private NewYangmaoFavoritesMapper newYangmaoFavoritesMapper;

    /**
     * 品类组dao
     */
    @Autowired
    private YangmaoFavoritesMapper favoritesMapper;

    /**
     * 品类模板dao
     */
    @Autowired
    private YangmaoTemplateSectionMapper sectionMapper;

    /**
     * 获取产品组
     * @return
     */
    @Override
    public List<YangmaoFavorites> getFavoritesList() {
        List<YangmaoFavorites> favoritesList = new ArrayList<>();
        favoritesList = newYangmaoFavoritesMapper.selectFavoritesList();
        return favoritesList;
    }


    /**
     * 邮件模板
     *
     * @param template 邮件模板实体
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(value="yangmaoTransactionManager", rollbackFor = Exception.class)
    public int insertEmailTemplate(YangmaoMailTemplate template,String[] amount) throws Exception {
        int result = 0;
        Date date = new Date();
        template.setCreateTime(date);
        template.setLastUpdateTime(date);
        template.setStatus(Constants.TEMPLATE_STATUS_NORMAL);
        result = templateMapper.insert(template);

        this.insertSection(amount,template.getTemplateId());

        return result;
    }

    /**
     * 通过id获取邮件模板
     *
     * @param templateId 邮件模板id
     * @return
     * @throws Exception
     */
    @Override
    public YangmaoMailTemplate selectEmailTemplateById(long templateId) throws Exception {
        YangmaoMailTemplate template = new YangmaoMailTemplate();
        if (templateId != 0) {
            template = templateMapper.selectByPrimaryKey(templateId);
        }
        return template;
    }


    /**
     * 插入品类
     * @param amount 数量
     * @param templateId 模板id
     * @throws Exception
     */
    public void insertSection(String[] amount,long templateId) throws Exception{
        for(int i = 0 ;i<amount.length;i++){
            YangmaoTemplateSection section = new YangmaoTemplateSection();
            section.setFavoritesId(Long.parseLong("0"));
            section.setSection("暂不记录");
            section.setSectionAmount(Integer.parseInt(amount[i]));
            section.setTemplateId(templateId);
            sectionMapper.insert(section);
        }
    }

    /**
     * 修改邮件模板
     * @param template 邮件模板实体
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(value="yangmaoTransactionManager", rollbackFor = Exception.class)
    public int updateEmailTemplate(YangmaoMailTemplate template,String[] amount) throws Exception {
        YangmaoTemplateSectionExample example = new YangmaoTemplateSectionExample();
        example.createCriteria().andTemplateIdEqualTo(template.getTemplateId());
        sectionMapper.deleteByExample(example);

        YangmaoMailTemplate mailTemplate = templateMapper.selectByPrimaryKey(template.getTemplateId());
        mailTemplate.setLastUpdateTime(new Date());
        mailTemplate.setContent(template.getContent());
        mailTemplate.setName(template.getName());
        mailTemplate.setTitle(template.getTitle());
        templateMapper.updateByPrimaryKeySelective(mailTemplate);

        this.insertSection(amount,template.getTemplateId());

        return 0;
    }

    /**
     * 获取邮件模板列表
     * @param page 分页
     * @param name 模板名称
     * @return
     * @throws Exception
     */
    @Override
    public List<MailTemplateModel> getTemplateListForPage(Page page, String name) throws Exception {
        List<MailTemplateModel> mailTemplateModels = new ArrayList<>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        page.setTotalElements(newYangmaoMailTemplateMapper.countEmailTemplateListForPage(map));
        map.put("start", page.getNumber() * page.getSize());
        map.put("size", page.getSize());
        mailTemplateModels = newYangmaoMailTemplateMapper.selectEmailTemplateListForPage(map);
        return mailTemplateModels;
    }

    /**
     * 通过id删除模板
     * @param templateId 模板id
     * @return
     * @throws Exception
     */
    @Override
    public int deleteEmailTemplate(long templateId) throws Exception {
        int result = 0;
        if (templateId != 0) {
            YangmaoMailTemplate template = templateMapper.selectByPrimaryKey(templateId);
            template.setStatus(Constants.TEMPLATE_STATUS_DELETE);
            template.setLastUpdateTime(new Date());
            result = templateMapper.updateByPrimaryKeySelective(template);
        }
        return result;
    }

    /**
     * 通过模板id获取品类列表
     * @param templateId 模板id
     * @return
     * @throws Exception
     */
    @Override
    public List<YangmaoTemplateSection> getTemplateSectionList(long templateId) throws Exception {
        List<YangmaoTemplateSection> templateSections = new ArrayList<>();
        YangmaoTemplateSectionExample example = new YangmaoTemplateSectionExample();
        example.createCriteria().andTemplateIdEqualTo(templateId);
        templateSections = sectionMapper.selectByExample(example);
        return templateSections;
    }
}
