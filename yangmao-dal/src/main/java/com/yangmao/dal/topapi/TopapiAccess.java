package com.yangmao.dal.topapi;

import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.TbkFavorites;
import com.taobao.api.domain.UatmTbkItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.request.TbkUatmFavoritesGetRequest;
import com.taobao.api.request.TbkUatmFavoritesItemGetRequest;
import com.taobao.api.response.TbkUatmFavoritesGetResponse;
import com.taobao.api.response.TbkUatmFavoritesItemGetResponse;
import com.yangmao.dal.dataobj.YangmaoFavorites;
import com.yangmao.dal.dataobj.YangmaoFavoritesItem;
import com.yangmao.util.PropertiesUtils;

public class TopapiAccess {
	
	static String appkey=PropertiesUtils.prop.get("app_key");
	static String appSecret=PropertiesUtils.prop.get("app_secret");
	static String url=PropertiesUtils.prop.get("taobaoke_url");
	static Long adZoneId=Long.valueOf(PropertiesUtils.prop.get("ad_zone_id"));  //必须是网站推广的长联接里面的adZoneId,不能是
	
	static Logger logger = LoggerFactory.getLogger(TopapiAccess.class);
	
	public static List<YangmaoFavoritesItem> getFavoritesItem(Long favoritesId)  {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, appSecret);
		TbkUatmFavoritesItemGetRequest req = new TbkUatmFavoritesItemGetRequest();
		req.setPlatform(1L);
		req.setPageSize(300L);
		req.setAdzoneId(adZoneId);
		req.setUnid("3456");
		req.setFavoritesId(favoritesId);
		req.setPageNo(1L);
		req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick,shop_title,zk_final_price_wap,event_start_time,event_end_time,tk_rate,status,type,click_url");
		TbkUatmFavoritesItemGetResponse rsp=null;
		try {
			rsp = client.execute(req);
		} catch (ApiException e) {
			logger.error("fail to get tbk favorates items"+JSON.toJSONString(e));
			return null;
		}
		logger.info("got tbk favorites id:"+favoritesId+" items are:"+JSON.toJSONString(rsp));
		if(rsp.isSuccess()){
			List<UatmTbkItem> favoriteItems= rsp.getResults();
			if(favoriteItems!=null&&!favoriteItems.isEmpty()){
				List<YangmaoFavoritesItem> yangmaoFavoritesItems=new ArrayList<YangmaoFavoritesItem>();
				for(UatmTbkItem favoriteItem:favoriteItems){
					YangmaoFavoritesItem yangmaoFavoritesItem=new YangmaoFavoritesItem();				
					yangmaoFavoritesItem.setClickUrl(favoriteItem.getClickUrl());
					yangmaoFavoritesItem.setFinalPrice(new BigDecimal(favoriteItem.getZkFinalPrice()));
					yangmaoFavoritesItem.setFinalPriceWap(new BigDecimal(favoriteItem.getZkFinalPriceWap()));
					List<String> imageList=favoriteItem.getSmallImages();
					if(imageList!=null&&!imageList.isEmpty()){
						String itemImages="";
						for(String image:imageList){
							itemImages=itemImages+image+",";
						}
						if(itemImages.length()>=1){
							itemImages=itemImages.substring(0, itemImages.length()-1);
						}		
						yangmaoFavoritesItem.setItemImages(itemImages);
					}
					
					yangmaoFavoritesItem.setItemUrl(favoriteItem.getItemUrl());
					yangmaoFavoritesItem.setNick(favoriteItem.getNick());
					yangmaoFavoritesItem.setNumIid(favoriteItem.getNumIid());
					yangmaoFavoritesItem.setOriginalPrice(new BigDecimal(favoriteItem.getReservePrice()));
					yangmaoFavoritesItem.setPictUrl(favoriteItem.getPictUrl());
					yangmaoFavoritesItem.setProvcity(favoriteItem.getProvcity());
					yangmaoFavoritesItem.setSellerId(favoriteItem.getSellerId());
					yangmaoFavoritesItem.setShopTitle(favoriteItem.getShopTitle());
					yangmaoFavoritesItem.setTitle(favoriteItem.getTitle());
					yangmaoFavoritesItem.setTkRate(new BigDecimal(favoriteItem.getTkRate()));
					yangmaoFavoritesItem.setVolume(favoriteItem.getVolume().intValue());
					yangmaoFavoritesItems.add(yangmaoFavoritesItem);
				}
				return yangmaoFavoritesItems;
			}else{//
				return null;
			}
		}else{
			return null;
		}
		//System.out.println(rsp.getBody());
		
	}

	//获取选品库列表
	public static List<YangmaoFavorites> getFavorites() {
		
		//List<TbkFavorites> favorites=new ArrayList<TbkFavorites>();
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, appSecret);
		TbkUatmFavoritesGetRequest req = new TbkUatmFavoritesGetRequest();
		req.setPageNo(1L);
		req.setPageSize(200L);
		req.setFields("favorites_title,favorites_id,type");
		req.setType(1L);
		TbkUatmFavoritesGetResponse rsp=null;
		try {
			rsp = client.execute(req);
		} catch (ApiException e) {
			logger.error("fail to get tbk favorates"+JSON.toJSONString(e));
			return null;
		}
		//TbkUatmFavoritesGetResponse response=JSON.parseObject(rsp, clazz)
		Date now=new Date();
		//System.out.println(rsp.getBody());
		logger.info("got tbk favorites are:"+JSON.toJSONString(rsp));
		List<TbkFavorites> favorites= rsp.getResults();
		if(favorites!=null&&!favorites.isEmpty()){
			List<YangmaoFavorites> yangmaoFavorites=new ArrayList<YangmaoFavorites>();
			for(TbkFavorites favorite:favorites){
				YangmaoFavorites yangmaoFavorite=new YangmaoFavorites();				
				yangmaoFavorite.setFavoritesId(favorite.getFavoritesId());
				yangmaoFavorite.setTitle(favorite.getFavoritesTitle());
				yangmaoFavorite.setType(favorite.getType().intValue());
				yangmaoFavorites.add(yangmaoFavorite);
			}
			return yangmaoFavorites;
		}else{//
			return null;
		}
		
		
	}

}
