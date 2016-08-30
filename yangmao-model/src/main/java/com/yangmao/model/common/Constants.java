package com.yangmao.model.common;

import java.math.BigDecimal;

public class Constants {
	public static String LANG_CN = "zh_CN";
	public static String LANG_EN = "en_US";

	public static String SYSTEM = "SYSTEM";



	//是否是管理员
	public static Byte TEMPLATE_STATUS_NORMAL = 0;
	public static Byte TEMPLATE_STATUS_DELETE = 1;

	//cookie
	public static String COOKIE_USER_TOKEN = "user_token";
	public static String COOKIE_USER_NAME = "user_name";
	
	public static String SOHO3Q_USER_TOKEN = "token";
	public static String SOHO3Q_SID = "sid";
	
	//user admin
	public static Byte USER_NOT_ADMIN = 0;//不是管理员
	public static Byte USER_ADMIN = 1; //是管理员
	
	//user status
	public static Byte USER_STATUS_NORMAL = 0;//正常
	public static Byte USER_STATUS_FROZEN = 1; //冻结
	public static Byte USER_STATUS_DELETED = 2; //删除
	
	public static BigDecimal D0 = new BigDecimal(0);
	
	//邮件实例状态
	public static Byte EMAIL_STATUS_NORMAL = 0;
	public static Byte EMAIL_STATUS_OFFSHIFT = 1;
	
	//邮件地址状态
	public static Byte SENDER_STATUS_NORMAL = 0;
	public static Byte SENDER_STATUS_OFFSHIFT = 1;
	


}
