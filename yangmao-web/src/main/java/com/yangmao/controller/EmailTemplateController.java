package com.yangmao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * email 模板控制器
 *
 * Created by liyongfeng on 16/8/23.
 */
@Controller
@RequestMapping(RouteKey.ADMIN)
public class EmailTemplateController {

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
    public String insertEmailTemplate(){

        return "redirect:email_template_list.html";
    }

    @RequestMapping(RouteKey.EMAIL_TEMPLATE_LIST)
    public void emailTemplateList(){

    }

    @RequestMapping(RouteKey.MODIFY_EMAIL_TEMPLATE)
    public void modifyEmailTemplate(){

    }

    @RequestMapping(RouteKey.UPDATE_EMAIL_TEMPLATE)
    public String updateEmailTemplate(){
        return "redirect:email_template_list.html";
    }

    @RequestMapping(RouteKey.DELETE_EMAIL_TEMPALTE)
    public String deleteEmailTemplate(){
        return "redirect:email_template_list.html";
    }

}
