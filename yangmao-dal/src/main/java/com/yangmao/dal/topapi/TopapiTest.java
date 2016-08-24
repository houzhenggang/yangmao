package com.yangmao.dal.topapi;

import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.TbkFavorites;
import com.taobao.api.domain.UatmTbkItem;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.request.TbkUatmFavoritesGetRequest;
import com.taobao.api.request.TbkUatmFavoritesItemGetRequest;
import com.taobao.api.response.TbkUatmFavoritesGetResponse;
import com.taobao.api.response.TbkUatmFavoritesItemGetResponse;

public class TopapiTest {
	static String appkey="23436181";
	static String appSecret="085bce71cca1615554466da64dab6473";
	static String url="http://gw.api.taobao.com/router/rest";
	static Long adZoneId=59986355L;  //必须是网站推广的长联接里面的adZoneId,不能是
	public static void main(String[] args) throws ApiException {
		List<TbkFavorites> favorites=getFavorites();
		
		
		//选品
		for(TbkFavorites favorite:favorites){
			System.out.println("选品组："+JSON.toJSONString(favorite));
			List<UatmTbkItem> items=getFavoritesItem(favorite.getFavoritesId());
			for(UatmTbkItem item:items){
				System.out.println("选品："+JSON.toJSONString(item));
			}
		}
		

		

	}
	
	private static List<UatmTbkItem> getFavoritesItem(Long favoritesId) throws ApiException {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, appSecret);
		TbkUatmFavoritesItemGetRequest req = new TbkUatmFavoritesItemGetRequest();
		req.setPlatform(1L);
		req.setPageSize(20L);
		req.setAdzoneId(adZoneId);
		req.setUnid("3456");
		req.setFavoritesId(favoritesId);
		req.setPageNo(1L);
		req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick,shop_title,zk_final_price_wap,event_start_time,event_end_time,tk_rate,status,type,click_url");
		TbkUatmFavoritesItemGetResponse rsp = client.execute(req);
		if(rsp.isSuccess()){
			return rsp.getResults();
		}else{
			return null;
		}
		//System.out.println(rsp.getBody());
		
	}

	//获取选品库列表
	private static List<TbkFavorites> getFavorites() throws ApiException {
		
		//List<TbkFavorites> favorites=new ArrayList<TbkFavorites>();
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, appSecret);
		TbkUatmFavoritesGetRequest req = new TbkUatmFavoritesGetRequest();
		req.setPageNo(1L);
		req.setPageSize(20L);
		req.setFields("favorites_title,favorites_id,type");
		req.setType(1L);
		TbkUatmFavoritesGetResponse rsp = client.execute(req);
		//TbkUatmFavoritesGetResponse response=JSON.parseObject(rsp, clazz)
		
		//System.out.println(rsp.getBody());
		
		return rsp.getResults();
		
		
	}

}
