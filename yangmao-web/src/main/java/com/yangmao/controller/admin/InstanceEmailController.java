package com.yangmao.controller.admin;

import com.yangmao.controller.RouteKey;
import com.yangmao.model.admin.dto.EmailInstanceSectionModel;
import com.yangmao.model.admin.dto.EmailInstanceTemplateModel;
import com.yangmao.model.common.Page;
import com.yangmao.service.InstanceEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * 保存邮件实例
     * @return
     */
    @RequestMapping(RouteKey.INSERT_INSTANCE_EMAIL)
    public String insertInstanceEmail(){
        return "redirect:instance_email_list.html";
    }

    @RequestMapping(RouteKey.INSTANCE_EMAIL_LIST)
    public void instanceEmailList(Page page,String title,Model model){

    }



}
