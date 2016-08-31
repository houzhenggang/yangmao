package com.yangmao.service.impl;

import com.yangmao.dal.dao.NewYangmaoFavoritesMapper;
import com.yangmao.dal.dao.NewYangmaoMailTemplateMapper;
import com.yangmao.dal.dao.YangmaoEmailMapper;
import com.yangmao.dal.dao.YangmaoMailTemplateMapper;
import com.yangmao.dal.dataobj.YangmaoFavorites;
import com.yangmao.dal.dataobj.YangmaoMailTemplate;
import com.yangmao.model.admin.dto.MailTemplateModel;
import com.yangmao.model.common.Constants;
import com.yangmao.model.common.Page;
import com.yangmao.service.EmailTemplateService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public int insertEmailTemplate(YangmaoMailTemplate template) throws Exception {
        int result = 0;
        Date date = new Date();
        template.setCreateTime(date);
        template.setLastUpdateTime(date);
        template.setStatus(Constants.TEMPLATE_STATUS_NORMAL);
        result = templateMapper.insert(template);
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
     * 修改邮件模板
     * @param template 邮件模板实体
     * @return
     * @throws Exception
     */
    @Override
    public int updateEmailTemplate(YangmaoMailTemplate template) throws Exception {
        template.setLastUpdateTime(new Date());
        templateMapper.updateByPrimaryKeySelective(template);
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
}
