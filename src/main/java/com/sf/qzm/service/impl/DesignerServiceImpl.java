package com.sf.qzm.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sf.qzm.bean.biz.Designer;
import com.sf.qzm.bean.constant.Zone;
import com.sf.qzm.dao.DesignerDao;
import com.sf.qzm.dto.biz.DesignerDTO;
import com.sf.qzm.service.DesignerService;

@Service
@Resource
public class DesignerServiceImpl implements DesignerService {

	@Resource
	private DesignerDao designerDao;
	@Override
	public void saveOrUpdate(Designer designer) {
		if(designer.getDesignerId()!=null){
			Integer[] zoneIds=designer.getZoneIds();
			if(zoneIds!=null){
				List<Integer> ids=new ArrayList<Integer>();
				Collections.addAll(ids, zoneIds);
				designerDao.deleteZone(designer.getDesignerId());
				designerDao.saveZoneBatch(designer.getDesignerId(), ids);
			}
			
			designerDao.update(designer);
		}else{
			designerDao.save(designer);
		}
	}

	@Override
	public DesignerDTO get(Integer designerId) {
		if(designerId==null){return null;}
		Designer param=new Designer();
		param.setDesignerId(designerId);
		return designerDao.get(param);
	}

	@Override
	public List<DesignerDTO> all() {
		return designerDao.list(new Designer());
	}

	@Override
	public List<Zone> rootByDesigner(Integer designerId) {
		return designerDao.rootByDesigner(designerId);
	}

	@Override
	public List<Zone> listByDesigner(Integer zoneParentId, Integer designerId) {
		Zone zone=new Zone();
		zone.setParentId(zoneParentId);
		return designerDao.listByDesigner(zone, designerId);
	}

}
