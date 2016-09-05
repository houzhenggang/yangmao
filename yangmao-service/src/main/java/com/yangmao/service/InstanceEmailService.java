package com.yangmao.service;

import com.yangmao.dal.dataobj.YangmaoFavorites;
import com.yangmao.dal.dataobj.YangmaoFavoritesItem;
import com.yangmao.dal.dataobj.YangmaoMailInstance;
import com.yangmao.dal.dataobj.YangmaoReplaceField;
import com.yangmao.model.admin.dto.EmailInstanceTemplateModel;
import com.yangmao.model.admin.dto.FavoritesItemsModel;
import com.yangmao.model.admin.dto.SectionUploadTemplateModel;
import com.yangmao.model.common.Page;

import java.util.List;

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

    /**
     * 通过品类组id获取商品列表
     * @param favoritesId 品类组id
     * @return
     * @throws Exception
     */
    public List<YangmaoFavoritesItem> getCommodityList(long favoritesId) throws Exception;

    /**
     * 通过产品id列表查询产品信息
     * @param itemsId 新品id列表
     * @return
     * @throws Exception
     */
    public List<FavoritesItemsModel> getCommodityListByItemId(String[] itemsId) throws Exception;

    /**
     * 获取替换模板键值对
     * @return
     * @throws Exception
     */
    public List<YangmaoReplaceField> getReplaceKeyValueList() throws Exception;

    /**
     * 保存邮件实例
     * @param instance 邮件实体
     * @param instanceItemId 产品id
     * @return
     */
    public int insertInstanceEmail(YangmaoMailInstance instance, String[] instanceItemId) throws Exception;

    /**
     * 获取生成邮件实体列表
     * @param page 分页
     * @param title 邮件标题
     * @return
     */
    public List<YangmaoMailInstance> getInstanceEmailList(Page page, String title)  throws Exception;
}
