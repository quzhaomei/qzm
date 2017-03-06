package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.biz.Activity;
import com.sf.qzm.bean.biz.ActivityImage;
import com.sf.qzm.dao.ActivityDao;
import com.sf.qzm.dto.biz.ActivityDTO;
import com.sf.qzm.service.ActivityService;

@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {
	
	@Resource
	private ActivityDao activityDao;
	@Override
	public ActivityDTO get(Activity activity) {
		return activityDao.get(activity);
	}

	@Override
	public void save(Activity activity) {
		activityDao.save(activity);
	}

	@Override
	public void update(Activity activity) {
		if(activity.getActivityId()==null){return;}
		
		activityDao.update(activity);
		//更新活动图片
		activityDao.emptyImage(activity.getActivityId());
		List<ActivityImage> images=activity.getImages();
		if(images!=null&&images.size()>0){
			activityDao.saveImage(activity.getActivityId(), activity.getImages());
		}
		
	}

	@Override
	public List<ActivityDTO> list(Activity activity) {
		return activityDao.list(activity);
	}

	@Override
	public ActivityDTO getById(Integer activityId) {
		if(activityId==null){return null;}
		Activity activity=new Activity();
		activity.setActivityId(activityId);
		
		return  activityDao.get(activity);
	}

}
