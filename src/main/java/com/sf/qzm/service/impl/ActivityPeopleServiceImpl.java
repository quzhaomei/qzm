package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.biz.ActivityPeople;
import com.sf.qzm.dao.ActivityPeopleDao;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.biz.ActivityPeopleDTO;
import com.sf.qzm.service.ActivityPeopleService;

@Service
@Transactional
public class ActivityPeopleServiceImpl implements ActivityPeopleService {
	@Resource
	private ActivityPeopleDao activityPeopleDao;

	@Override
	public ActivityPeopleDTO getById(Integer peopleId) {
		if(peopleId==null){return null;}
		ActivityPeople param=new ActivityPeople();
		param.setPeopleId(peopleId);
		return activityPeopleDao.get(param);
	}

	@Override
	public void saveOrUpdate(ActivityPeople people) {
		if(people.getPeopleId()==null){
			activityPeopleDao.save(people);
		}else{
			activityPeopleDao.update(people);
		}
	}

	@Override
	public PageDTO<List<ActivityPeopleDTO>> list(PageDTO<ActivityPeople> page) {
		List<ActivityPeopleDTO> dateList = activityPeopleDao.getByPage(page);
		PageDTO<List<ActivityPeopleDTO>> pageDate = new PageDTO<List<ActivityPeopleDTO>>();
		pageDate.setParam(dateList);
		pageDate.setPageIndex(page.getPageIndex());
		pageDate.setPageSize(page.getPageSize());
		Integer count = activityPeopleDao.count(page.getParam());
		pageDate.setCount(count+0);
		count = count % page.getPageSize() == 0 ? count / page.getPageSize()
				: count / page.getPageSize() + 1;
		pageDate.setTotalPage(count);
		return pageDate;
	}

	@Override
	public ActivityPeopleDTO get(ActivityPeople people) {
		return activityPeopleDao.get(people);
	}
}
