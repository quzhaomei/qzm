package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.constant.HouseStyle;

public interface HouseStyleService {
	void saveOrUpdate(HouseStyle houseStyle);
	HouseStyle getById(Integer styleId);
	List<HouseStyle> all();
	void delete(Integer styleId);
	
}
