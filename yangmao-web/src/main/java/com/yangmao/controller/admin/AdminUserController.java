package com.yangmao.controller.admin;


import com.yangmao.controller.RouteKey;
import com.yangmao.dal.dataobj.YangmaoUser;
import com.yangmao.model.common.Constants;
import com.yangmao.model.common.Page;
import com.yangmao.service.admin.AdminUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户控制器
 * Created by liyongfeng on 2015/12/31.
 */
@Controller
@RequestMapping(RouteKey.USER)
public class AdminUserController {

    /**
     * 用户服务
     */
    @Autowired
    private AdminUserService adminUserService;

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(AdminUserController.class);

    /**
     * 添加用户
     */
    @RequestMapping(RouteKey.ADD_USER)
    public void addUser(){

    }

    /**
     * 保存用户信息至数据库
     * @return
     */
    @RequestMapping(RouteKey.SAVE_USER)
    public String saveUser(YangmaoUser yangmaoUser, String isNotAdmin) {
        if("on".equalsIgnoreCase(isNotAdmin)){
            yangmaoUser.setIsAdmin(Constants.USER_ADMIN);
        }else{
            yangmaoUser.setIsAdmin(Constants.USER_NOT_ADMIN);
        }
        try {
            adminUserService.insertUser(yangmaoUser);
        } catch (Exception e) {
            logger.error("AdminUserController.saveUser",e);
        }
        return "redirect:user_list.html";
    }

    /**
     * 跳转至修改用户信息
     * @param userId 用户id
     */
    @RequestMapping(RouteKey.MODIFY_USER)
    public void modifyUser(long userId,Model model){
        YangmaoUser user = new YangmaoUser();
        try {
            user = adminUserService.selectOneByUserId(userId);
        } catch (Exception e) {
            logger.error("AdminUserController.modifyUser",e);
        }
        model.addAttribute("data",user);
    }

    /**
     * 修改用户信息并保存至数据库
     * @return
     */
    @RequestMapping(RouteKey.UPDATE_USER)
    public String updateUser(YangmaoUser yangmaoUser,String isNotAdmin){
        if("on".equalsIgnoreCase(isNotAdmin)){
            yangmaoUser.setIsAdmin(Constants.USER_ADMIN);
        }else{
            yangmaoUser.setIsAdmin(Constants.USER_NOT_ADMIN);
        }
        try {
            adminUserService.updateUser(yangmaoUser);
        } catch (Exception e) {
            logger.error("AdminUserController.updateUser",e);
        }
        return "redirect:user_list.html";
    }

    /**
     * 获取用户列表
     */
    @RequestMapping(RouteKey.USER_LIST)
    public void userList(Page page, String name, Model model){
        List<YangmaoUser> userList = new ArrayList<>();
        try {
            userList = adminUserService.selectUserListByPage(page,name);
        } catch (Exception e) {
            logger.error("AdminUserController.updateUser",e);
        }
        model.addAttribute("data",userList);
        model.addAttribute("page",page);
        model.addAttribute("name",name);
    }

    /**
     * 删除用户信息
     * @param userId 用户id
     * @return
     */
    @RequestMapping(RouteKey.UPDATE_STATUS)
    public String updateStatus(long userId,int status){
        try {
            adminUserService.updateUserStatus(userId,status);
        } catch (Exception e) {
            logger.error("AdminUserController.updateStatus",e);
        }
        return "redirect:user_list.html";
    }
}
