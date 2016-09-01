package com.sf.qzm.controller.back;

import org.springframework.web.bind.annotation.RequestMapping;

import com.sf.qzm.annotation.MenuTag;

@RequestMapping("webAppSet")
@MenuTag(code = "webAppSet", name = "站点信息设置", sequence =2, type =0,icon="icon-earth")
public class WebAppSetController {
	@RequestMapping("/index")
	@MenuTag(code = "webAppSet-index", name = "首页", sequence =0, type = 1)
	public void index(){
		
	}
}
