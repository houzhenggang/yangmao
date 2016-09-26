package com.yangmao.service;

import java.util.Date;

import com.yangmao.model.GetEmailsResult;
import com.yangmao.model.common.YangmaoException;

public interface EmailService {

	GetEmailsResult getEmailsResult(String ipAddr,String isTest) throws YangmaoException;

	void invalidateSender(String email) throws YangmaoException;

	
}
