package com.sf.qzm.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.biz.Company;
import com.sf.qzm.dao.CompanyDao;
import com.sf.qzm.dao.CompanyZoneDao;
import com.sf.qzm.dto.biz.CompanyDTO;
import com.sf.qzm.service.CompanyService;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

	@Resource
	private CompanyDao companyDao;
	
	@Resource
	private CompanyZoneDao companyZoneDao;
	
	@Override
	public void saveOrUpdate(Company company) {
		if(company.getCompanyId()!=null){
			Integer[] zoneIds=company.getZoneIds();
			if(zoneIds!=null){
				List<Integer> ids=new ArrayList<Integer>();
				Collections.addAll(ids, zoneIds);
				companyZoneDao.deleteBatch(company.getCompanyId());
				if(ids.size()>0){
					companyZoneDao.saveBatch(company.getCompanyId(), ids);
				}
			}
			Integer[] typeIds=company.getCompanyTypeIds();
			if(typeIds!=null){
				companyDao.clearType(company.getCompanyId());
				if(typeIds.length>0){
					companyDao.saveType(company.getCompanyId(), typeIds);
				}
			}
			
			companyDao.update(company);
		}else{
			
			companyDao.save(company);
			Integer[] typeIds=company.getCompanyTypeIds();
			if(typeIds!=null){
				companyDao.clearType(company.getCompanyId());
				if(typeIds.length>0){
					companyDao.saveType(company.getCompanyId(), typeIds);
				}
			}
		}
	}

	@Override
	public CompanyDTO get(Integer companyId) {
		if(companyId==null){return null;}
		Company param=new Company();
		param.setCompanyId(companyId);
		return companyDao.get(param);
	}

	@Override
	public List<CompanyDTO> all() {
		return companyDao.list(new Company());
	}

	@Override
	public void clear() {
		companyDao.clear();
	}

}
