package com.yangmao.service.impl;

import com.yangmao.dal.dao.*;
import com.yangmao.dal.dataobj.*;
import com.yangmao.model.admin.dto.EmailInstanceSectionModel;
import com.yangmao.model.admin.dto.EmailInstanceTemplateModel;
import com.yangmao.model.admin.dto.FavoritesItemsModel;
import com.yangmao.model.admin.dto.SectionUploadTemplateModel;
import com.yangmao.model.common.Constants;
import com.yangmao.model.common.Messages;
import com.yangmao.model.common.Page;
import com.yangmao.model.exception.AdminServiceException;
import com.yangmao.service.InstanceEmailService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.util.*;

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
     * 新商品dao
     */
    @Autowired
    private NewYangmaoFavoritesItemMapper newFavoritesItemMapper;

    /**
     * 替换模板键值对dao
     */
    @Autowired
    private YangmaoReplaceFieldMapper replaceFieldMapper;

    /**
     * 邮件实例dao
     */
    @Autowired
    private YangmaoMailInstanceMapper instanceMapper;

    /**
     * 邮件实体商品dao
     */
    @Autowired
    private YangmaoMailInstanceItemMapper instanceItemMapper;

    /**
     * 新邮件实体dao
     */
    @Autowired
    private NewYangmaoMailInstanceMapper newYangmaoMailInstanceMapper;

    /**
     * @param templateId 模板id
     * @return
     */
    @Override
    public EmailInstanceTemplateModel selectTemplate(long templateId) throws Exception {
        EmailInstanceTemplateModel emailInstanceTemplateModel = new EmailInstanceTemplateModel();
        List<EmailInstanceSectionModel> sectionModels = new ArrayList<>();
        if (templateId == 0) {
            throw new AdminServiceException(Messages.TEMPLATE_ID_IS_NULL_CODE, Messages.TEMPLATE_ID_IS_NULL_MSG);
        }

        YangmaoMailTemplateExample example = new YangmaoMailTemplateExample();
        example.createCriteria().andStatusEqualTo(Constants.TEMPLATE_STATUS_NORMAL).andTemplateIdEqualTo(templateId);
        List<YangmaoMailTemplate> templates = templateMapper.selectByExampleWithBLOBs(example);
        if (templates.isEmpty()) {
            throw new AdminServiceException(Messages.TEMPLATE_NOT_FIND_CODE, Messages.TEMPLATE_NOT_FIND_MSG);
        }

        YangmaoMailTemplate template = templates.get(0);
        emailInstanceTemplateModel.setContent(template.getContent());
        emailInstanceTemplateModel.setTemplateId(templateId);
        emailInstanceTemplateModel.setTitle(template.getTitle());

        YangmaoTemplateSectionExample sectionExample = new YangmaoTemplateSectionExample();
        sectionExample.createCriteria().andTemplateIdEqualTo(templateId);
        List<YangmaoTemplateSection> sections = sectionMapper.selectByExample(sectionExample);

        for (YangmaoTemplateSection section : sections) {
            EmailInstanceSectionModel sectionModel = new EmailInstanceSectionModel();
            sectionModel.setSection(section.getSection());
            sectionModel.setSectionAmount(section.getSectionAmount());
            sectionModel.setFavoritesId(section.getFavoritesId());
            sectionModel.setSectionId(section.getSectionId());

            List<YangmaoFavoritesItem> favoritesItems = this.getCommodityList(section.getFavoritesId());
            List<FavoritesItemsModel> favoritesItemsModels = new ArrayList<>();
            for (YangmaoFavoritesItem favoritesItem : favoritesItems){
                FavoritesItemsModel favoritesItemsModel = new FavoritesItemsModel();
                favoritesItemsModel.setItemId(favoritesItem.getItemId());
                favoritesItemsModel.setTitle(favoritesItem.getTitle());
                favoritesItemsModel.setNumIid(favoritesItem.getNumIid());
                favoritesItemsModel.setPictUrl(favoritesItem.getPictUrl());
                favoritesItemsModel.setNick(favoritesItem.getNick());
                favoritesItemsModel.setOriginalPrice(favoritesItem.getOriginalPrice());
                favoritesItemsModel.setFinalPrice(favoritesItem.getFinalPrice());
                favoritesItemsModel.setVolume(favoritesItem.getVolume());
                favoritesItemsModels.add(favoritesItemsModel);
            }
            sectionModel.setFavoritesItemsModels(favoritesItemsModels);

            sectionModels.add(sectionModel);
        }
        emailInstanceTemplateModel.setSectionModelList(sectionModels);

        return emailInstanceTemplateModel;
    }

    /**
     * 通过品类组id获取商品
     *
     * @param favoritesId 品类组id
     * @return
     * @throws Exception
     */
    @Override
    public List<YangmaoFavoritesItem> getCommodityList(long favoritesId) throws Exception {
        List<YangmaoFavoritesItem> favoritesItems = new ArrayList<>();

        if (favoritesId == 0) {
            throw new AdminServiceException(Messages.FAVORITES_ID_IS_NULL_CODE, Messages.FAVORITES_ID_IS_NULL_MSG);
        }

        Map<String, Object> map = new HashedMap();
        map.put("favoritesId", favoritesId);
        Calendar c = Calendar.getInstance();
        Date endTime = c.getTime();
        c.add(Calendar.MONTH, -1);
        Date startTime = c.getTime();
        map.put("startTime", startTime);
        map.put("endTime", endTime);

        favoritesItems = newFavoritesItemMapper.selectFavoritesItemsListByCreateTimeAndId(map);

        return favoritesItems;
    }

    /**
     * 通过商品id查询商品详尽信息
     * @param itemsId 新品id列表
     * @return
     * @throws Exception
     */
    @Override
    public List<FavoritesItemsModel> getCommodityListByItemId(List<String> itemsId) throws Exception {
        List<FavoritesItemsModel> favoritesItems = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("itemIds",itemsId);
        favoritesItems = newFavoritesItemMapper.selectFavoritesItemsListByItemsId(map);
        return favoritesItems;
    }

    /**
     * 获取替换模板键值对列表
     * @return
     * @throws Exception
     */
    @Override
    public List<YangmaoReplaceField> getReplaceKeyValueList() throws Exception {
        List<YangmaoReplaceField> replaceFields = new ArrayList<>();
        YangmaoReplaceFieldExample example = new YangmaoReplaceFieldExample();
        example.createCriteria().andReplaceFieldIdIsNotNull();
        replaceFields = replaceFieldMapper.selectByExample(example);
        return replaceFields;
    }

    /**
     * 保存邮件实体
     * @param instance 邮件实体
     * @param instanceItemId 产品id
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(value="yangmaoTransactionManager", rollbackFor = Exception.class)
    public int insertInstanceEmail(YangmaoMailInstance instance, String[] instanceItemId) throws Exception {
        int result = 0;
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        cal.add(Calendar.WEEK_OF_MONTH,1);
        Date expireDate = cal.getTime();
        instance.setStatus(Constants.TEMPLATE_STATUS_NORMAL);
        instance.setCreateTime(date);
        instance.setExpireTime(expireDate);
        instance.setLastUpdateTime(date);
        instanceMapper.insert(instance);

        for(int i = 0;i<instanceItemId.length;i++){
            String[] itemAndSectionIds =  instanceItemId[i].split("-");
            YangmaoMailInstanceItem item = new YangmaoMailInstanceItem();
            item.setSectionId(Long.parseLong(itemAndSectionIds[1]));
            item.setItemId(Long.parseLong(itemAndSectionIds[0]));
            item.setMailInstanceId(instance.getMailInstanceId());
            instanceItemMapper.insert(item);
        }
        return 0;
    }

    /**
     * 获取邮件列表
     * @param page 分页
     * @param title 邮件标题
     * @return
     * @throws Exception
     */
    @Override
    public List<YangmaoMailInstance> getInstanceEmailList(Page page, String title)  throws Exception{
        List<YangmaoMailInstance> instances = new ArrayList<>();
        Map<String,Object> map = new HashedMap();
        map.put("name", title);
        int count = newYangmaoMailInstanceMapper.countsInstanceEmailForPage(map);
        page.setTotalElements(count);
        map.put("start", page.getNumber() * page.getSize());
        map.put("size", page.getSize());
        instances = newYangmaoMailInstanceMapper.selectInstanceEmailForPage(map);
        return instances;
    }
}
