package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.biz.CompanyZone;
import com.sf.qzm.bean.constant.Zone;
import com.sf.qzm.dto.biz.CompanyZoneDTO;

public interface CompanyZoneDao {
	void save(@Param(value="companyZone") CompanyZone companyZone);
	void saveBatch(@Param(value="companyId")Integer companyId,
			@Param(value="zoneIds") List<Integer> zoneId);
	
	void delete(@Param(value="companyZone")CompanyZone companyZone);
	void deleteBatch(@Param(value="companyId")Integer companyId);
	
	CompanyZoneDTO list(@Param(value="companyId")Integer companyId);
	
	List<Zone> rootByCompany(@Param(value="companyId")Integer companyId);
	List<Zone> listByCompany(@Param(value="zone")Zone zone,@Param(value="companyId")Integer companyId);
}
