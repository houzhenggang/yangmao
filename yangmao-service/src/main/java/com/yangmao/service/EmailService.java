package com.yangmao.service;

import java.util.List;

import com.yangmao.model.GetEmailsResult;
import com.yangmao.model.common.YangmaoException;

public interface EmailService {

	GetEmailsResult getEmailsResult(String ipAddr,String isTest) throws YangmaoException;

	
}
