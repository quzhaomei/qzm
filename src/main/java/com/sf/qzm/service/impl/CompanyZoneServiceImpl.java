package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.biz.CompanyZone;
import com.sf.qzm.bean.constant.Zone;
import com.sf.qzm.dao.CompanyZoneDao;
import com.sf.qzm.service.CompanyZoneService;

@Service
@Transactional
public class CompanyZoneServiceImpl implements CompanyZoneService {

	@Resource
	private CompanyZoneDao companyZoneDao;
	@Override
	public void delete(Integer companyId) {
		if(companyId==null){return;}
		CompanyZone param=new CompanyZone();
		param.setCompanyId(companyId);
		companyZoneDao.delete(param);
	}

	@Override
	public void batchSave(Integer companyId, List<Integer> zoneId) {
		companyZoneDao.saveBatch(companyId, zoneId);
	}
	
	@Override
	public List<Zone> root(Integer companyId) {
		return companyZoneDao.rootByCompany(companyId);
	}

	@Override
	public List<Zone> list(Integer parentId, Integer companyId) {
		if(parentId==null){return null;}
		Zone param=new Zone();
		param.setParentId(parentId);
		return companyZoneDao.listByCompany(param, companyId);
	}

}
