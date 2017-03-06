package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.biz.ActivityPeople;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.biz.ActivityPeopleDTO;

public interface ActivityPeopleDao {
	ActivityPeopleDTO get(@Param(value="people") ActivityPeople people);
	void save(@Param(value="people") ActivityPeople people);
	void update(@Param(value="people") ActivityPeople people);
	
	List<ActivityPeopleDTO> getByPage(@Param(value="page") PageDTO<ActivityPeople> page);
	int count(@Param(value="people") ActivityPeople people);
	
}
