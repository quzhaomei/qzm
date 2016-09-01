package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.biz.Company;
import com.sf.qzm.dto.biz.CompanyDTO;

public interface CompanyService {
	void saveOrUpdate(Company company);
	CompanyDTO get(Integer companyId);
	 List<CompanyDTO> all();
	 
	 void clear();
}
