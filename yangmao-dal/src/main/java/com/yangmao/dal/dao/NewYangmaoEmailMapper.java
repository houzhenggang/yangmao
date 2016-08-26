package com.yangmao.dal.dao;
import java.util.List;
import java.util.Map;

import com.yangmao.dal.dataobj.YangmaoEmail;



public interface NewYangmaoEmailMapper {
	/**
	 *
	 * @param map
	 * @return
     */
	public List<YangmaoEmail> getAndLockEmails(Map<String,Object> map);



	

}