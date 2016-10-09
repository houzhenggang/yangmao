package com.yangmao.service.impl;

import com.alibaba.fastjson.JSON;

import com.yangmao.dal.dao.YangmaoUserMapper;
import com.yangmao.dal.dataobj.YangmaoUser;
import com.yangmao.dal.dataobj.YangmaoUserExample;
import com.yangmao.model.UserInfo;
import com.yangmao.model.UserLoginInfo;
import com.yangmao.model.common.Constants;
import com.yangmao.model.common.Messages;
import com.yangmao.model.common.YangmaoException;
import com.yangmao.service.TokenService;
import com.yangmao.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private YangmaoUserMapper yangmaoUserMapper;
    
    static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
	@Override
	public UserInfo getUserInfo(String token) {
		UserInfo userInfo=null;
		return userInfo;
	}

	@Override
	public UserInfo getUserInfo(Cookie[] cookies) {
		UserInfo userInfo=null;
		if(cookies==null ||cookies.length<=0){
			return null;
		}else{
			for (int i = 0; i < cookies.length; i++) {
				if (Constants.COOKIE_USER_TOKEN.equalsIgnoreCase(cookies[i].getName())) {
					String token=cookies[i].getValue();
					userInfo=tokenService.getUserInfoByToken(token);
				}
			}
		}		
		return userInfo;
	}

	@Override
	public UserLoginInfo registerUser(String userName, String password) throws YangmaoException, Exception {
		//UserLoginInfo userLoginInfo=new UserLoginInfo();
		
		//check if the user exist
		YangmaoUserExample userExample=new YangmaoUserExample();
		userExample.createCriteria().andNameEqualTo(userName);
		List<YangmaoUser> users=yangmaoUserMapper.selectByExample(userExample);
		if(users.size()>0 && StringUtils.isNoneBlank(userName)){//name already exist
			logger.info("user name "+userName+" already exists");
			throw new YangmaoException(Messages.USER_ALREADY_EXIST_CODE,Messages.USER_ALREADY_EXIST_MSG);
		}
		
		//insert user table
		Date now=new Date();
		YangmaoUser yangmaoUser=new YangmaoUser();
		yangmaoUser.setCreateTime(now);
		yangmaoUser.setIsAdmin(Constants.USER_NOT_ADMIN);
		yangmaoUser.setName(userName);
		yangmaoUser.setPassword(password);
		yangmaoUser.setStatus(Constants.USER_STATUS_NORMAL);
		yangmaoUser.setLastUpdateTime(now);
		yangmaoUserMapper.insert(yangmaoUser);
		logger.info("new user was inserted:"+ JSON.toJSONString(yangmaoUser));
		
		//login
		UserLoginInfo userLoginInfo=login(userName, password);
		return userLoginInfo;
	}

	@Override
	public UserLoginInfo login(String userName, String password) throws YangmaoException, Exception {
		UserLoginInfo userLoginInfo=new UserLoginInfo();
		//定位用户
		YangmaoUserExample userExample=new YangmaoUserExample();
		userExample.createCriteria().andNameEqualTo(userName);
		List<YangmaoUser> users=yangmaoUserMapper.selectByExample(userExample);
		if(users==null||userName.isEmpty()){//name not exist
			logger.error("user name "+userName+" not exists");
			throw new YangmaoException(Messages.USER_NOT_EXIST_CODE,Messages.USER_NOT_EXIST_MSG);
		}
		YangmaoUser user=users.get(0);
		
		//验证密码
		if(!password.equals(user.getPassword())){
			logger.error("password not match");
			throw new YangmaoException(Messages.PASSWORD_NOT_MATCH_CODE,Messages.PASSWORD_NOT_MATCH_MSG);
		}
		
		//生成token
		UserInfo userInfo=new UserInfo();
		userInfo.setName(userName);
		userInfo.setUserId(user.getUserId());
		String token = tokenService.createToken(userInfo);
		userLoginInfo.setName(userName);
		userLoginInfo.setToken(token);
		userLoginInfo.setUserId(user.getUserId());
		return userLoginInfo;
	}
	
	
}
