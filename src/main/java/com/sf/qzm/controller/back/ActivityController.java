package com.sf.qzm.controller.back;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.qzm.annotation.MenuTag;
import com.sf.qzm.bean.biz.Activity;
import com.sf.qzm.bean.biz.ActivityPeople;
import com.sf.qzm.bean.biz.Channel;
import com.sf.qzm.controller.BaseController;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.biz.ActivityDTO;
import com.sf.qzm.dto.biz.ActivityPeopleDTO;
import com.sf.qzm.dto.biz.ChannelDTO;
import com.sf.qzm.service.ActivityPeopleService;
import com.sf.qzm.service.ActivityService;
import com.sf.qzm.service.ChannelService;
import com.sf.qzm.util.other.Constant;
import com.sf.qzm.util.other.JsonUtils;

@Controller
@RequestMapping("activity")
@MenuTag(code = "activity", name = "活动管理", sequence = 5, type = 0,icon="icon-toys")
public class ActivityController extends BaseController {
	@Resource
	private ActivityService activityService;
	@Resource
	private ActivityPeopleService activityPeopleService;
	
	
	@Resource
	private ChannelService channelService;
	public static final Integer BROAD_CAST_TYPE_ID=15;
	/**
	 * 所有活动
	 */
	@MenuTag(code = "all-activity", name = "所有活动", sequence = 1, type = 1)
	@RequestMapping(value = "/all-activity")
	public String activity(String operator, Map<String, Object> map,HttpServletRequest request) {
		if (Constant.LIST.equals(operator)) {
			List<ActivityDTO> datas=activityService.list(new Activity());
			map.put(Constant.JSON, JsonUtils.object2json(datas));
			return Constant.JSON;
		} 
		
		Channel channel=new Channel();
		channel.setTypeId(BROAD_CAST_TYPE_ID);
		List<ChannelDTO> channels= channelService.list(channel);
		map.put("channels", channels);
		return "admin/all-activity";
	}
	
	/**
	 * 添加活动
	 */
	@MenuTag(code = "add-activity", name = "添加活动", sequence = 1, type = 2,parentCode="all-activity")
	@RequestMapping(value = "/add-activity")
	@ResponseBody
	public JsonDTO addActivity(Activity activity,HttpServletRequest request) {
		JsonDTO json=new JsonDTO();
		activity.setStatus(0);
		activity.setCreateDate(new Date());
		activity.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
		try {
			activityService.save(activity);
			json.setStatus(1).setMessage("活动添加成功！");
		} catch (Exception e) {
			json.setStatus(0).setMessage("活动添加失败！后台出现异常！");
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 添加活动
	 */
	@MenuTag(code = "update-activity", name = "更新活动", sequence = 1, type = 2,parentCode="all-activity")
	@RequestMapping(value = "/update-activity")
	@ResponseBody
	public JsonDTO updateActivity(@RequestBody Activity activity,HttpServletRequest request) {
		JsonDTO json=new JsonDTO();
		try {
			activityService.update(activity);
			json.setStatus(1).setMessage("活动更新成功！");
		} catch (Exception e) {
			json.setStatus(0).setMessage("活动更新失败！后台出现异常！");
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 活动报名信息
	 */
	@MenuTag(code = "all-activity-people", name = "活动报名信息", sequence = 2, type = 1)
	@RequestMapping(value = "/all-activity-people")
	public String activityPeople(String operator,
			Integer pageIndex,Integer pageSize,String direction,
			Map<String, Object> map,ActivityPeople activityPeople) {
		if (Constant.LIST.equals(operator)) {
			PageDTO<ActivityPeople> page=new PageDTO<ActivityPeople>();
			page.setPageIndex(pageIndex);
			page.setPageSize(pageSize);
			page.setParam(activityPeople);
			if(direction==null){direction="asc";}
			page.setDirection(direction);
			PageDTO<List<ActivityPeopleDTO>> datas=activityPeopleService.list(page);
			map.put(Constant.JSON, JsonUtils.object2json(datas));
			return Constant.JSON;
		} 
		//查找所有活动
		List<ActivityDTO> datas=activityService.list(new Activity());
		map.put("activitys",datas);
		return "admin/all-activity-people";
	}
}
