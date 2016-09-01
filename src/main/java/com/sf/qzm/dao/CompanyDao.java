package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.biz.Company;
import com.sf.qzm.dto.biz.CompanyDTO;

public interface CompanyDao {
	void save(@Param(value="company") Company company);
	void update(@Param(value="company")Company company);
	CompanyDTO get(@Param(value="company")Company company);
	List<CompanyDTO> list(@Param(value="company")Company company);
	int count(@Param(value="company")Company company);
	
	void saveType(@Param(value="companyId")Integer companyId,@Param(value="typeIds") Integer[] typeIds);
	void clearType(@Param(value="companyId")Integer companyId);
	
	void clear();
}
