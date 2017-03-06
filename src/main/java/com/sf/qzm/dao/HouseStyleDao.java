package com.sf.qzm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.biz.CompanyType;
import com.sf.qzm.bean.constant.CompanyToStyle;
import com.sf.qzm.bean.constant.HouseStyle;

public interface HouseStyleDao {
	void save(@Param(value="style") HouseStyle style);
	void update(@Param(value="style")HouseStyle style);
	HouseStyle get(@Param(value="style")HouseStyle style);
	List<HouseStyle> list(@Param(value="style")HouseStyle style);
	int count(@Param(value="style")HouseStyle style);
	
	
	List<Map<String,Object>> getDefaultPrice(@Param(value="types")List<CompanyType> types);
	void savePrice(@Param(value="sets")List<CompanyToStyle> sets);
	void clearPrice(@Param(value="styleId")Integer styleId,@Param(value="companyId") Integer companyId);

	
	List<Map<String,Object>> getCompanyPriceByStyle(@Param(value="types")List<CompanyType> types
			,@Param(value="styleId")Integer styleId);
	
}
