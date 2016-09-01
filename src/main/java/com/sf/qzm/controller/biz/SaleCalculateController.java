package com.sf.qzm.controller.biz;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.qzm.annotation.MenuTag;
import com.sf.qzm.bean.biz.ChannelType;
import com.sf.qzm.controller.BaseController;

@Controller
@RequestMapping("requireTotal")
@MenuTag(code = "requireTotal", name = "电销派单系统", sequence = 3, type = 0,icon="icon-headset_mic")
public class SaleCalculateController extends BaseController{
	
	@MenuTag(code = "requireTotal-set", name = "派单系统设置", sequence = 3, type = 1 )
	@RequestMapping(value = "/requireTotal-set")
	@ResponseBody
	public void index1(ChannelType channel,HttpServletRequest request) {
		
	}
	@MenuTag(code = "requireTotal-hard-set", name = "全部硬装需求", sequence = 4, type = 1 )
	@RequestMapping(value = "/requireTotal-hard-set")
	@ResponseBody
	public void index2(ChannelType channel,HttpServletRequest request) {
		
	}
	@MenuTag(code = "requireTotal-soft-set", name = "全部软装需求", sequence = 5, type = 1 )
	@RequestMapping(value = "/requireTotal-soft-set")
	@ResponseBody
	public void index3(ChannelType channel,HttpServletRequest request) {
		
	}
	
}
