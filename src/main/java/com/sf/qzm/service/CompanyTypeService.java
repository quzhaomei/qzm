package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.biz.CompanyType;

public interface CompanyTypeService {
	void saveOrUpdate(CompanyType type);
	List<CompanyType> all();
}
