package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.constant.Zone;
import com.sf.qzm.dao.ZoneDao;
import com.sf.qzm.service.ZoneService;

@Service
@Transactional
public class ZoneServiceImpl implements ZoneService {
	
	@Resource
	private ZoneDao zoneDao;
	@Override
	public void saveOrUpdate(Zone zone) {
		if(zone.getZoneId()!=null){
			zoneDao.update(zone);
		}else{
			zoneDao.save(zone);
		}
	}

	@Override
	public Zone get(Integer id) {
		if(id==null){return null;}
		Zone param=new Zone();
		param.setZoneId(id);
		return zoneDao.get(param);
	}

	@Override
	public List<Zone> root() {
		return zoneDao.root();
	}

	@Override
	public List<Zone> list(Integer parentId) {
		if(parentId==null){return null;}
		Zone param=new Zone();
		param.setParentId(parentId);
		return zoneDao.list(param);
	}
	
}
