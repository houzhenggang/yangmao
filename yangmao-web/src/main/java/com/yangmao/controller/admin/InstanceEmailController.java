package com.yangmao.controller.admin;

import com.yangmao.controller.RouteKey;
import com.yangmao.dal.dataobj.YangmaoFavoritesItem;
import com.yangmao.dal.dataobj.YangmaoMailInstance;
import com.yangmao.dal.dataobj.YangmaoReplaceField;
import com.yangmao.model.admin.dto.EmailInstanceSectionModel;
import com.yangmao.model.admin.dto.EmailInstanceTemplateModel;
import com.yangmao.model.common.Page;
import com.yangmao.service.InstanceEmailService;
import org.omg.CORBA.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮件实例
 * Created by liyongfeng on 16/9/1.
 */
@Controller
@RequestMapping(RouteKey.ADMIN)
public class InstanceEmailController {

    /**
     * 实例服务
     */
    @Autowired
    private InstanceEmailService instanceEmailService;

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(InstanceEmailController.class);

    /**
     * 生成邮件实例
     * @param templateId 模板id
     */
    @RequestMapping(RouteKey.ADD_INSTANCE_EMAIL)
    public void addInstanceEmail(long templateId,Model model){
        EmailInstanceTemplateModel templateModel = new EmailInstanceTemplateModel();
        try {
            templateModel = instanceEmailService.selectTemplate(templateId);
        } catch (Exception e) {
            logger.error("InstanceEmailController.addInstanceEmail",e);
        }
        model.addAttribute("template",templateModel);
    }

    /**
     * 通过品类组id获取商品列表
     * @return
     */
    @ResponseBody
    @RequestMapping(RouteKey.GET_COMMODITY_LIST)
    public List<YangmaoFavoritesItem> getCommodityList(long favoritesId){
        List<YangmaoFavoritesItem> favoritesItems = new ArrayList<>();
        try {
            favoritesItems = instanceEmailService.getCommodityList(favoritesId);
        } catch (Exception e) {
            logger.error("InstanceEmailController.getCommodityList",e);
        }
        return favoritesItems;
    }

    /**
     * 通过商品id获取商品详情
     * @param itemsId 商品id
     * @return
     */
    @ResponseBody
    @RequestMapping(RouteKey.GET_COMMODITY_LIST_BY_ITEM_ID)
    public List<YangmaoFavoritesItem> getCommodityListByItemId(String[] itemsId){
        List<YangmaoFavoritesItem> favoritesItems =new ArrayList<>();
        try {
            favoritesItems = instanceEmailService.getCommodityListByItemId(itemsId);
        } catch (Exception e) {
            logger.error("InstanceEmailController.getCommodityListByItemId",e);
        }
        return favoritesItems;
    }

    /**
     * 获取替换表键值对
     * @return
     */
    @ResponseBody
    @RequestMapping(RouteKey.GET_REPLACE_KEY_VALUE_LIST)
    public List<YangmaoReplaceField> getReplaceKeyValueList(){
        List<YangmaoReplaceField> replaceFields = new ArrayList<>();
        try {
            replaceFields = instanceEmailService.getReplaceKeyValueList();
        } catch (Exception e) {
            logger.error("InstanceEmailController.getReplaceKeyValueList",e);
        }
        return replaceFields;
    }

    /**
     * 保存邮件实例
     * @return
     */
    @RequestMapping(RouteKey.INSERT_INSTANCE_EMAIL)
    public String insertInstanceEmail(YangmaoMailInstance instance,String[] instanceItemId){
        return "redirect:instance_email_list.html";
    }

    @RequestMapping(RouteKey.INSTANCE_EMAIL_LIST)
    public void instanceEmailList(Page page,String title,Model model){

    }



}
