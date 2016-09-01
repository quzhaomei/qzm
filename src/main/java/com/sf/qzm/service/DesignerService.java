package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.biz.Designer;
import com.sf.qzm.bean.constant.Zone;
import com.sf.qzm.dto.biz.DesignerDTO;

public interface DesignerService {
	void saveOrUpdate(Designer designer);
	DesignerDTO get(Integer designerId);
	List<DesignerDTO> all();
	List<Zone> rootByDesigner(Integer designerId);
	List<Zone> listByDesigner(Integer zoneParentId,Integer designerId);
}
