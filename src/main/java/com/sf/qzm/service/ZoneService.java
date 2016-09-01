package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.constant.Zone;

public interface ZoneService {
	void saveOrUpdate(Zone zone);
	Zone get(Integer id);
	
	List<Zone> root();
	
	List<Zone> list(Integer parentId);
	
	
}
