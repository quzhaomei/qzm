package com.sf.qzm.controller.nopower;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.qzm.bean.biz.ActivityPeople;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.service.ActivityPeopleService;
import com.sf.qzm.service.ActivityService;

@RequestMapping("/")
@Controller
public class MinisiteController {
	@Resource
	private ActivityService activityService;
	
	@Resource
	private ActivityPeopleService activityPeopleService;
	@RequestMapping(value="/{activityId}/res_{channelId}")
	public String activityRes(@PathVariable(value="activityId")Integer activityId,
			@PathVariable(value="channelId")Integer channelId,Map<String,Object> map){
		
		map.put("activity", activityService.getById(activityId));
		return "minisite/activity-res";
	}
	
	@RequestMapping(value="/doRes")
	@ResponseBody
	public JsonDTO activityRes(ActivityPeople activityPeople,Map<String,Object> map){
		JsonDTO result=new JsonDTO();
		ActivityPeople check=new ActivityPeople();
		check.setActivityId(activityPeople.getActivityId());
		check.setPhone(activityPeople.getPhone());
		if(activityPeopleService.get(check)!=null){
			result.setStatus(1).setMessage("该用户已经报名成功，请勿重复操作！");
		}else{
			activityPeople.setPeopleId(null);
			activityPeople.setCreateDate(new Date());
			try {
				activityPeopleService.saveOrUpdate(activityPeople);
				result.setStatus(1).setMessage("报名成功！");
			} catch (Exception e) {
				e.printStackTrace();
				result.setStatus(0).setMessage("报名时系统出现异常，请稍后再试！");
			}
		}
		return result;
	}
}
