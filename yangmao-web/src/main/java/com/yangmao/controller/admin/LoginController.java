package com.yangmao.controller.admin;

import com.yangmao.controller.RouteKey;
import com.yangmao.model.common.Constants;
import com.yangmao.util.CookieHelper;
import com.yangmao.util.PropertiesUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * 登录页面控制器
 * Created by liyongfeng on 2016/10/9.
 */
@Controller
@RequestMapping(RouteKey.USER_LOGIN)
public class LoginController {

    /**
     * 跳转至登录接口
     */
    @RequestMapping(RouteKey.DO_LOGOUT)
    public String logout(HttpServletResponse response){
        //set user_token
        CookieHelper.setCookie(
                Constants.COOKIE_USER_TOKEN
                , ""
                , PropertiesUtils.prop.get("domain.name")
                , "/"
                , 0  //cookie有效期0天
                , response
        );
        //set user_name
        CookieHelper.setCookie(
                Constants.COOKIE_USER_NAME
                , ""
                , PropertiesUtils.prop.get("domain.name")
                , "/"
                , 0  //cookie有效期0天
                , response
        );
        return "redirect:login.html";
    }

    /**
     * 跳转至登录页面
     */
    @RequestMapping(RouteKey.DO_LOGIN)
    public void login(){
    }
}
