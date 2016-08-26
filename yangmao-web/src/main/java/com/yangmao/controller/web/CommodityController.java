package com.yangmao.controller.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import com.yangmao.controller.RouteKey;
import com.yangmao.dal.dao.YangmaoClickMapper;
import com.yangmao.dal.dao.YangmaoFavoritesItemMapper;
import com.yangmao.dal.dao.YangmaoFavoritesMapper;
import com.yangmao.dal.dataobj.YangmaoClick;
import com.yangmao.dal.dataobj.YangmaoFavoritesItem;
import com.yangmao.service.quartz.TbkFavoritesUpdateTask;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(RouteKey.COMMODITY)
public class CommodityController {

	@Autowired
	private YangmaoFavoritesItemMapper yangmaoFavoritesItemMapper;
	
	@Autowired
	private YangmaoClickMapper yangmaoClickMapper;

    /**
     * 日志文件
     */
	static Logger logger = LoggerFactory.getLogger(CommodityController.class);
    
	//private String cmsURL = PropertiesUtils.prop.get("CMS_URL")+"ajax/cmsvalue/getvalue?channelName=MY3Q&cmsKey=&businessId=";

	/**
	 * 商品跳转
	 */
	@RequestMapping(value = RouteKey.REDIRECT)
	public String redirect(Long itemId,Long emailInstanceId,String email) {
		//找到对应的选品
		YangmaoFavoritesItem yangmaoFavoritesItem=yangmaoFavoritesItemMapper.selectByPrimaryKey(itemId);
		if(yangmaoFavoritesItem==null){
			return null;
		}
		
		//记录点击历史
		if(email!=null){
			YangmaoClick yangmaoClick=new YangmaoClick();
			yangmaoClick.setCreateTime(new Date());
			yangmaoClick.setEmail(email);
			yangmaoClick.setItemId(itemId);
			yangmaoClick.setMailInstanceId(emailInstanceId);
			yangmaoClickMapper.insert(yangmaoClick);
		}

		return String.format("redirect:%s", yangmaoFavoritesItem.getClickUrl());
	}


}

