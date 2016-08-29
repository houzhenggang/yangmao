package com.yangmao.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.yangmao.service.EmailService;
import com.yangmao.util.DateUtil;
import com.yangmao.util.StringUtil;
import com.yangmao.dal.dao.NewYangmaoEmailMapper;
import com.yangmao.dal.dao.YangmaoEmailGettingHistoryMapper;
import com.yangmao.dal.dao.YangmaoEmailMapper;
import com.yangmao.dal.dao.YangmaoEmailSenderMapper;
import com.yangmao.dal.dao.YangmaoMailInstanceMapper;
import com.yangmao.dal.dataobj.YangmaoEmail;
import com.yangmao.dal.dataobj.YangmaoEmailExample;
import com.yangmao.dal.dataobj.YangmaoEmailGettingHistory;
import com.yangmao.dal.dataobj.YangmaoEmailGettingHistoryExample;
import com.yangmao.dal.dataobj.YangmaoEmailSender;
import com.yangmao.dal.dataobj.YangmaoEmailSenderExample;
import com.yangmao.dal.dataobj.YangmaoMailInstance;
import com.yangmao.dal.dataobj.YangmaoMailInstanceExample;
import com.yangmao.model.GetEmailsResult;
import com.yangmao.model.common.Constants;
import com.yangmao.model.common.Messages;
import com.yangmao.model.common.YangmaoException;


@Service
@Transactional(value = "yangmaoTransactionManager", rollbackFor = Exception.class)
public class EmailServiceImpl implements EmailService{

	static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	
    @Autowired
    private YangmaoEmailMapper YangmaoEmailMapper;
    
    @Autowired
    private NewYangmaoEmailMapper newYangmaoEmailMapper;
    
    @Autowired
    private YangmaoEmailGettingHistoryMapper yangmaoEmailGettingHistoryMapper;
    
    @Autowired
    private YangmaoMailInstanceMapper yangmaoMailInstanceMapper;
    
    @Autowired
    private YangmaoEmailSenderMapper yangmaoEmailSenderMapper;
    
    static private Long totalAmount=5000000L;//总数
    static private Long section=6L;//总数分段, 0~1000000,1000000~2000000,...4000000~无穷大
    static private Integer emailLimitByIp=20000;//每个ip地址每天发送限制

	
	@Override
	public GetEmailsResult getEmailsResult(String ipAddress) throws YangmaoException {
		Date now=new Date();
		Long number=now.getTime();
		String date=DateUtil.formatDate(now,DateUtil.FORMAT_DEFAULT);
		Date startToday=DateUtil.parseDate(date, DateUtil.FORMAT_DEFAULT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startToday);		
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date endToday = calendar.getTime();	
		
		//判断该ip当天发送量是否超限
		if(!StringUtil.isNullOrEmpty(ipAddress)){
			YangmaoEmailGettingHistoryExample yangmaoEmailGettingHistoryExample=new YangmaoEmailGettingHistoryExample();
			yangmaoEmailGettingHistoryExample.createCriteria().andIpAddressEqualTo(ipAddress)
											.andCreateTimeBetween(startToday, endToday);
			int count=yangmaoEmailGettingHistoryMapper.countByExample(yangmaoEmailGettingHistoryExample);
			if(count>emailLimitByIp){
				logger.info("ip address:"+ipAddress+" has sent "+count+" emials,already exceeded the limit:"+emailLimitByIp);
				throw new YangmaoException(Messages.EXCEED_EMAILS_LIMIT_CODE,Messages.EXCEED_EMAILS_LIMIT_MSG);
			}
		}
		
		//获取邮件实例,随机选
		YangmaoMailInstanceExample yangmaoMailInstanceExample=new YangmaoMailInstanceExample();
		yangmaoMailInstanceExample.createCriteria().andStatusEqualTo(Constants.EMAIL_STATUS_NORMAL)
													.andExpireTimeGreaterThan(now);
		yangmaoMailInstanceExample.setOrderByClause("mail_instance_id desc");
		List<YangmaoMailInstance> mailInstances=yangmaoMailInstanceMapper.selectByExample(yangmaoMailInstanceExample);
		YangmaoMailInstance mailInstance=null;
		if(mailInstances==null||mailInstances.isEmpty()){
			logger.info("no valid mail instance exist");
			throw new YangmaoException(Messages.NO_VALID_MAILS_CODE,Messages.NO_VALID_MAILS_MSG);	
		}else{
			Integer size=mailInstances.size();
			Long m=number%size.longValue();
			mailInstance=mailInstances.get(m.intValue());
			//mailInstance=mailInstances.get(0);
		}
		
		
		//获取发信者信息，随机选
		YangmaoEmailSenderExample yangmaoEmailSenderExample=new YangmaoEmailSenderExample();
		yangmaoEmailSenderExample.createCriteria().andStatusEqualTo(Constants.SENDER_STATUS_NORMAL);
		List<YangmaoEmailSender> senders=yangmaoEmailSenderMapper.selectByExample(yangmaoEmailSenderExample);
		YangmaoEmailSender sender=null;
		if(senders==null||senders.isEmpty()){
			logger.info("no valid mail sender exist");
			throw new YangmaoException(Messages.NO_VALID_SENDER_CODE,Messages.NO_VALID_SENDER_MSG);	
		}else{//随机选一个sender
			Integer size=senders.size();
			Long m=number%size.longValue();
			sender=senders.get(size.intValue());
		}
		
		
		//获取0 到section之间的一个随机数		
		Long i=number%section;
		
		//确定id范围
		Long startId=i*totalAmount/(section-1);
		Long endId=null;
		if(i<section-1){
			endId=(i+1)*totalAmount/(section-1);
		}
		Long instanceId=mailInstance.getMailInstanceId();
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("startId", startId);
		map.put("endId", endId);
		map.put("instanceId", instanceId);
		
		List<YangmaoEmail> emails=newYangmaoEmailMapper.getAndLockEmails(map);
		List<String> emailStingList=new ArrayList<String>();
		List<Long> emailIdList=new ArrayList<Long>();
		for(YangmaoEmail email:emails){
			emailStingList.add(email.getEmail());
			emailIdList.add(email.getEmailId());
		}
		GetEmailsResult getEmailsResult=new GetEmailsResult();
		getEmailsResult.setEmailContent(mailInstance.getContent());
		getEmailsResult.setEmailTitle(mailInstance.getTitle());
		getEmailsResult.setReceivers(emailStingList);
		getEmailsResult.setSenderEmail(sender.getEmail());
		getEmailsResult.setSenderName(sender.getName());
		getEmailsResult.setSenderPassword(sender.getPassword());
		
		//更新email表
		YangmaoEmail record=new YangmaoEmail();
		record.setLastEmailTime(now);
		record.setEmailInstanceId(mailInstance.getMailInstanceId());
		YangmaoEmailExample example=new YangmaoEmailExample();
		example.createCriteria().andEmailIdIn(emailIdList);		
		YangmaoEmailMapper.updateByExampleSelective(record, example);
		
		//插入获取记录
		YangmaoEmailGettingHistory history=new YangmaoEmailGettingHistory();
		history.setAmount(emailIdList.size());
		history.setCreateTime(now);
		history.setIpAddress(ipAddress);
		history.setMailInstanceId(mailInstance.getMailInstanceId());
		yangmaoEmailGettingHistoryMapper.insert(history);
		
		logger.info("got mail list:"+JSON.toJSONString(getEmailsResult));
		return getEmailsResult;

	}


}
