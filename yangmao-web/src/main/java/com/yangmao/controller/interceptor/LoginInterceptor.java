package com.yangmao.controller.interceptor;


import com.yangmao.dal.dataobj.YangmaoUser;
import com.yangmao.model.UserInfo;
import com.yangmao.model.common.Constants;
import com.yangmao.service.admin.AdminUserService;
import com.yangmao.service.TokenService;
import com.yangmao.util.CookieHelper;
import com.yangmao.util.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 登录拦截
 * Created by liyongfeng on 2016/2/20.
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 唯一验证服务
     */
    @Autowired
    private TokenService tokenService;

    /**
     * 用户服务
     */
    @Autowired
    private AdminUserService adminUserService;
    /**
     * 拦截器
     */
    static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    static Map<String, String> props = PropertiesUtils.prop;


    /**
     * 前置拦截
     * @param request
     * @param response
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String path = request.getRequestURI();
        String token = CookieHelper.getCookie(Constants.COOKIE_USER_TOKEN,request);
        UserInfo userInfo = tokenService.getUserInfoByToken(token);
        if(userInfo != null){
            YangmaoUser rebatesUser = adminUserService.selectOneByUserId(userInfo.getUserId());
            if(rebatesUser.getIsAdmin() == Constants.USER_ADMIN){
                return true;
            }
        }
        try {
            request.getSession().setAttribute("returnPath",path);
            String backendUrl = props.get("backend.url");
            String adminUrl = props.get("admin.uri");
            response.sendRedirect(backendUrl + adminUrl);
        } catch (Exception e) {
            logger.error("fail to get properties from config.progerties", e);
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
