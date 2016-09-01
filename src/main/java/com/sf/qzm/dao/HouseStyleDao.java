package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.constant.HouseStyle;

public interface HouseStyleDao {
	void save(@Param(value="style") HouseStyle style);
	void update(@Param(value="style")HouseStyle style);
	HouseStyle get(@Param(value="style")HouseStyle style);
	List<HouseStyle> list(@Param(value="style")HouseStyle style);
	int count(@Param(value="style")HouseStyle style);
}
