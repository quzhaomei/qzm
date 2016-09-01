package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.biz.CompanyType;

public interface CompanyTypeDao {
	void save(@Param(value="type") CompanyType type);
	void update(@Param(value="type")CompanyType type);
	CompanyType get(@Param(value="type")CompanyType type);
	List<CompanyType> list(@Param(value="type")CompanyType type);
}
