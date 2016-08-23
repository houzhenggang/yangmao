package com.yangmao.service.quartz;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.yangmao.dal.dao.YangmaoFavoritesItemMapper;
import com.yangmao.dal.dao.YangmaoFavoritesMapper;
import com.yangmao.dal.dataobj.YangmaoFavorites;
import com.yangmao.dal.dataobj.YangmaoFavoritesExample;
import com.yangmao.dal.dataobj.YangmaoFavoritesItem;
import com.yangmao.dal.dataobj.YangmaoFavoritesItemExample;
import com.yangmao.dal.topapi.TopapiAccess;
import com.yangmao.model.common.Constants;
import com.yangmao.model.common.YangmaoException;
import com.yangmao.util.PropertiesUtils;

@Transactional(value = "yangmaoTransactionManager", rollbackFor = Exception.class)
public class TbkFavoritesUpdateTask {
	
	@Autowired
	private YangmaoFavoritesMapper yangmaoFavoritesMapper;
	
	@Autowired
	private YangmaoFavoritesItemMapper yangmaoFavoritesItemMapper;
	
		
	
	static Logger logger = LoggerFactory.getLogger(TbkFavoritesUpdateTask.class);
	
	public void doWork() {		
		logger.info("start to update tbk favorites...");	
		
//		RebatesGlobalLock globalLock=newRebatesGlobalLockMapper.lockResource(Constants.LOCK_PULL_SOHO3Q_ORDERS);
//		if(globalLock!=null){
//			logger.info("got the global lock for refreshing order status.");
//		}else{//没有拿到lock,说明其他实例已经hold lock并且正在执行该方法，则不执行该方法
//			logger.info("other instance is holding the lock and refreshing order status, quit the method.");
//			return;
//		}
		
		Date now=new Date();
		//获得所有选品组列表
		List<YangmaoFavorites> favorates=TopapiAccess.getFavorites();
		if(favorates!=null&&!favorates.isEmpty()){
			for(YangmaoFavorites favorite:favorates){
				//查看该选聘组是否已经存在
				Long yangmaoFavoriteId=null;
				YangmaoFavoritesExample yangmaoFavoritesExample= new YangmaoFavoritesExample();
				yangmaoFavoritesExample.createCriteria().andFavoritesIdEqualTo(favorite.getFavoritesId());
				List<YangmaoFavorites> yangmaoFavorites=yangmaoFavoritesMapper.selectByExample(yangmaoFavoritesExample);
				if(yangmaoFavorites!=null&&!yangmaoFavorites.isEmpty()){//已经存在，则更新一下
					YangmaoFavorites yangmaoFavorite=yangmaoFavorites.get(0);
					yangmaoFavorite.setLastUpdateTime(now);
					yangmaoFavorite.setTitle(favorite.getTitle());
					yangmaoFavoritesMapper.updateByPrimaryKey(yangmaoFavorite);
					yangmaoFavoriteId=yangmaoFavorite.getYangmaoFavoritesId();
				}else{//不存在,需要插入
					favorite.setCreateTime(now);
					favorite.setLastUpdateTime(now);
					yangmaoFavoritesMapper.insert(favorite);
					yangmaoFavoriteId=favorite.getYangmaoFavoritesId();
				}
				
				//查找该选聘组下所有选品
				List<YangmaoFavoritesItem> favoritesItems=TopapiAccess.getFavoritesItem(favorite.getFavoritesId());
				if(favoritesItems!=null&&!favoritesItems.isEmpty()){//找到了所有的选品
					for(YangmaoFavoritesItem favoritesItem:favoritesItems){
						//查看该选品选品是否已经存在
						YangmaoFavoritesItemExample yangmaoFavoritesItemExample= new YangmaoFavoritesItemExample();
						yangmaoFavoritesItemExample.createCriteria().andNumIidEqualTo(favoritesItem.getNumIid())
																	.andYangmaoFavoritesIdEqualTo(yangmaoFavoriteId);
						List<YangmaoFavoritesItem> yangmaoFavoritesItems=yangmaoFavoritesItemMapper.selectByExample(yangmaoFavoritesItemExample);
						if(yangmaoFavoritesItems!=null&&!yangmaoFavoritesItems.isEmpty()){//已经存在，则更新一下
							YangmaoFavoritesItem yangmaoFavoriteItem=yangmaoFavoritesItems.get(0);
							yangmaoFavoriteItem.setClickUrl(favoritesItem.getClickUrl());
							yangmaoFavoriteItem.setFinalPrice(favoritesItem.getFinalPrice());
							yangmaoFavoriteItem.setFinalPriceWap(favoritesItem.getFinalPriceWap());
							yangmaoFavoriteItem.setLastUpdateTime(now);
							yangmaoFavoriteItem.setOriginalPrice(favoritesItem.getOriginalPrice());
							yangmaoFavoriteItem.setPictUrl(favoritesItem.getPictUrl());
							yangmaoFavoriteItem.setTitle(favoritesItem.getTitle());
							yangmaoFavoriteItem.setTkRate(favoritesItem.getTkRate());						
							yangmaoFavoritesItemMapper.updateByPrimaryKey(yangmaoFavoriteItem);
						}else{//不存在,需要插入
							favoritesItem.setCreateTime(now);
							favoritesItem.setLastUpdateTime(now);
							favoritesItem.setYangmaoFavoritesId(yangmaoFavoriteId);						
							yangmaoFavoritesItemMapper.insert(favoritesItem);
						}
					}					
				}
			}
		}
//		
		
		
	}
	


	
	
}
