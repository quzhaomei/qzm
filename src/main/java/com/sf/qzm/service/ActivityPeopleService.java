package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.biz.ActivityPeople;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.biz.ActivityPeopleDTO;

public interface ActivityPeopleService {
	ActivityPeopleDTO getById(Integer peopleId);
	ActivityPeopleDTO get(ActivityPeople people);
	void saveOrUpdate( ActivityPeople people);
	
	PageDTO<List<ActivityPeopleDTO>> list(PageDTO<ActivityPeople> page);
}
