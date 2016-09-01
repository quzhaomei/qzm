package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.biz.CompanyType;
import com.sf.qzm.dao.CompanyTypeDao;
import com.sf.qzm.service.CompanyTypeService;

@Service
@Transactional
public class CompanyTypeServiceImpl implements CompanyTypeService {
	
	@Resource
	private CompanyTypeDao companyTypeDao;
	@Override
	public void saveOrUpdate(CompanyType type) {
		if(type.getTypeId()!=null){
			companyTypeDao.update(type);
		}else{
			companyTypeDao.save(type);
		}
	}

	@Override
	public List<CompanyType> all() {
		
		return companyTypeDao.list(new CompanyType());
		
	}

}
