package com.sf.qzm.service;

import java.util.List;
import java.util.Map;

import com.sf.qzm.bean.biz.CompanyType;
import com.sf.qzm.bean.constant.HouseStyle;

public interface HouseStyleService {
	void saveOrUpdate(HouseStyle houseStyle);
	HouseStyle getById(Integer styleId);
	List<HouseStyle> all();
	void delete(Integer styleId);
	
	List<Map<String,Object>> getDefaultPrice(List<CompanyType> types);//获取房型对应的业务的默认价格
	
	List<Map<String,Object>> getCompanyPriceByStyle(List<CompanyType> types ,Integer styleId);//获取房型对应业务给公司对价格
}
