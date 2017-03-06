package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.biz.Activity;
import com.sf.qzm.bean.biz.ActivityImage;
import com.sf.qzm.dto.biz.ActivityDTO;

public interface ActivityDao {
	ActivityDTO get(@Param(value="activity") Activity activity);
	void save(@Param(value="activity") Activity activity);
	void update(@Param(value="activity") Activity activity);
	
	List<ActivityDTO> list(@Param("activity")Activity activity);
	
	
	void emptyImage(@Param("activityId")Integer activityId);
	void saveImage(@Param("activityId")Integer activityId,@Param("images")List<ActivityImage> images);
}
