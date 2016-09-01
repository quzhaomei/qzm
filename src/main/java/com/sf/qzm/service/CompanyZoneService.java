package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.constant.Zone;

public interface CompanyZoneService {
	void delete(Integer companyId);
	void batchSave(Integer companyId,List<Integer> zoneId);
	
	public List<Zone> root(Integer companyId) ;

	public List<Zone> list(Integer parentId, Integer companyId) ;
	
}
