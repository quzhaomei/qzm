package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.constant.Zone;

public interface ZoneDao {
	void save(@Param(value="zone") Zone zone);
	void update(@Param(value="zone")Zone zone);
	
	
	Zone get(@Param(value="zone")Zone zone);
	List<Zone> root();
	List<Zone> list(@Param(value="zone")Zone zone);
	int count(@Param(value="zone")Zone zone);
	
	
}
