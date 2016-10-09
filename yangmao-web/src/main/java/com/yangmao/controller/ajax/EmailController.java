package com.yangmao.controller.ajax;

import javax.servlet.http.HttpServletRequest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yangmao.controller.RouteKey;
import com.yangmao.model.GetEmailsResult;
import com.yangmao.model.common.Messages;
import com.yangmao.model.common.ResultCode;
import com.yangmao.model.common.YangmaoException;
import com.yangmao.service.EmailService;
import com.yangmao.util.StringUtil;


@Controller
@RequestMapping(RouteKey.PREFIX_AJAX)
public class EmailController {
    @Autowired
    private EmailService emailService;    
    
    static Logger logger = LoggerFactory.getLogger(EmailController.class);
	
    //获取发邮件信息
    @ResponseBody
	@RequestMapping(value = RouteKey.GETTING_EMAILS, method = RequestMethod.GET)
	public ResultCode<GetEmailsResult> getEmails(String isTest,HttpServletRequest httpServletRequest) {
		ResultCode<GetEmailsResult> result=new ResultCode<GetEmailsResult>();		
		//check params
		//String ipAddr=httpServletRequest.getRemoteAddr();
		String ipAddr = httpServletRequest.getHeader("X-Real-IP");
		
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
    
    //更新发送者生效时间
    @ResponseBody
	@RequestMapping(value = RouteKey.INVALIDATE_SENDER, method = RequestMethod.POST)
	public ResultCode<Object> invalidateSender(String email) {
    	logger.info("invalidate email "+email+" to one day after");
		ResultCode<Object> result=new ResultCode<Object>();		
		if(StringUtil.isNullOrEmpty(email)){
			result.setErrCode(Messages.MISSING_REQUIRED_PARAMS_CODE);
			result.setErrMsg(Messages.MISSING_REQUIRED_PARAMS_MSG);
			return result;
		}
		
		//create the order
		try {
			//Date time=DateUtil.parseDate(effectiveTime, DateUtil.FORMAT_ALL);
			emailService.invalidateSender(email);
		} catch (YangmaoException e) {
			result.setErrCode(e.getErrCode());
			result.setErrMsg(e.getErrMsg());
		}
		return result;

	}
    
}
