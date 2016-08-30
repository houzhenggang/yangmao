package com.yangmao.controller.ajax;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.commons.jxpath.ri.compiler.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.Constants;
import com.yangmao.controller.RouteKey;
import com.yangmao.model.GetEmailsResult;
import com.yangmao.model.common.ResultCode;
import com.yangmao.model.common.YangmaoException;
import com.yangmao.service.EmailService;


@Controller
@RequestMapping(RouteKey.PREFIX_AJAX)
public class EmailController {
    @Autowired
    private EmailService emailService;    
    
    static Logger logger = LoggerFactory.getLogger(EmailController.class);
	
    //创建一个新的订单
    @ResponseBody
	@RequestMapping(value = RouteKey.GETTING_EMAILS, method = RequestMethod.GET)
	public ResultCode<GetEmailsResult> createRebatesOrder(String isTest,HttpServletRequest httpServletRequest) {
		ResultCode<GetEmailsResult> result=new ResultCode<GetEmailsResult>();		
		//check params
		String ipAddr=httpServletRequest.getRemoteAddr();
		
		//create the order
		try {
			GetEmailsResult getEmailsResult=emailService.getEmailsResult(ipAddr,isTest);
			result.setData(getEmailsResult);
		} catch (YangmaoException e) {
			result.setErrCode(e.getErrCode());
			result.setErrMsg(e.getErrMsg());
		}
		return result;

	}
    
}
