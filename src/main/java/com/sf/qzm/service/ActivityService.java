package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.biz.Activity;
import com.sf.qzm.dto.biz.ActivityDTO;

public interface ActivityService {
	ActivityDTO getById(Integer activityId);
	ActivityDTO get(Activity activity);
	void save( Activity activity);
	void update( Activity activity);
	
	List<ActivityDTO> list(Activity activity);
}
