package com.yangmao.service;



import com.yangmao.model.UserInfo;
import com.yangmao.model.UserLoginInfo;
import com.yangmao.model.common.YangmaoException;

import javax.servlet.http.Cookie;

public interface UserService {
	
	//根据usertoken获取用户信息
	UserInfo getUserInfo(String token);

	UserInfo getUserInfo(Cookie[] cookies);

	UserLoginInfo registerUser(String userName, String password) throws YangmaoException,Exception;
	
	UserLoginInfo login(String userName, String password) throws YangmaoException,Exception;
}
