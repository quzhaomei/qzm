package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.biz.Designer;
import com.sf.qzm.bean.constant.Zone;
import com.sf.qzm.dto.biz.DesignerDTO;

public interface DesignerDao {
	void save(@Param(value="designer") Designer designer);
	void update(@Param(value="designer")Designer designer);
	DesignerDTO get(@Param(value="designer")Designer designer);
	List<DesignerDTO> list(@Param(value="designer")Designer designer);

	
	
	void saveZoneBatch(@Param(value="designerId")Integer designerId,
			@Param(value="zoneIds") List<Integer> zoneIds);
	void deleteZone(@Param(value="designerId")Integer designerId);
	List<Zone> rootByDesigner(@Param(value="designerId")Integer designerId);
	List<Zone> listByDesigner(@Param(value="zone")Zone zone,@Param(value="designerId")Integer designerId);
}
