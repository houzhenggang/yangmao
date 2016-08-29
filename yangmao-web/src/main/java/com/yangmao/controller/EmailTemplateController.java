package com.yangmao.controller;

import com.yangmao.dal.dataobj.YangmaoMailTemplate;
import com.yangmao.model.common.Page;
import com.yangmao.service.EmailTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


/**
 * email 模板控制器
 *
 * Created by liyongfeng on 16/8/23.
 */
@Controller
@RequestMapping(RouteKey.ADMIN)
public class EmailTemplateController {

    /**
     * 邮件模板服务
     */
    @Autowired
    private EmailTemplateService emailTemplateService;

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(EmailTemplateController.class);

    /**
     * 添加模板
     */
    @RequestMapping(RouteKey.ADD_EMAIL_TEMPLATE)
    public void addEmailTemplate(){

    }

    /**
     * 保存模板
     * @return
     */
    @RequestMapping(RouteKey.INSERT_EMAIL_TEMPLATE)
    public String insertEmailTemplate(YangmaoMailTemplate template){
        try {
            int result = emailTemplateService.insertEmailTemplate(template);
        } catch (Exception e) {
            logger.error("EmailTemplateController.insertEmailTemplate",e);
        }
        return "redirect:email_template_list.html";
    }

    /**
     * 获取模板列表
     * @param page 分页
     * @param name 名称
     * @param model 载体
     */
    @RequestMapping(RouteKey.EMAIL_TEMPLATE_LIST)
    public void emailTemplateList(Page page,String name,Model model){
        List<YangmaoMailTemplate> templateList = new ArrayList<>();
        try {
            templateList =  emailTemplateService.getTemplateListForPage(page,name);
        } catch (Exception e) {
            logger.error("EmailTemplateController.emailTemplateList", e);
        }
        model.addAttribute("data",templateList);
        model.addAttribute("page",page);
        model.addAttribute("name",name);
    }

    /**
     * 通过id查询需要修改模板详情
     * @param templateId 模板id
     * @param model 载体
     */
    @RequestMapping(RouteKey.MODIFY_EMAIL_TEMPLATE)
    public void modifyEmailTemplate(long templateId,Model model){
        YangmaoMailTemplate template = new YangmaoMailTemplate();
        try {
            template  = emailTemplateService.selectEmailTemplateById(templateId);
        } catch (Exception e) {
            logger.error("EmailTemplateController.modifyEmailTemplate", e);
        }
        model.addAttribute("template",template);
    }

    /**
     * 修改模板并保存数据库
     * @param template 模板
     * @return
     */
    @RequestMapping(RouteKey.UPDATE_EMAIL_TEMPLATE)
    public String updateEmailTemplate(YangmaoMailTemplate template){
        try {
           int result = emailTemplateService.updateEmailTemplate(template);
        } catch (Exception e) {
            logger.error("EmailTemplateController.updateEmailTemplate", e);
        }
        return "redirect:email_template_list.html";
    }

    /**
     * 通过模板id删除模板
     * @return
     */
    @RequestMapping(RouteKey.DELETE_EMAIL_TEMPALTE)
    public String deleteEmailTemplate(long templateId) {
        try {
           int result = emailTemplateService.deleteEmailTemplate(templateId);
        } catch (Exception e) {
            logger.error("EmailTemplateController.deleteEmailTemplate", e);
        }
        return "redirect:email_template_list.html";
    }

}
