package com.sf.qzm.controller.biz;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sf.qzm.annotation.MenuTag;

@Controller
@RequestMapping("myOrder")
@MenuTag(code = "myOrder", name = "订单跟踪", sequence = 5, type = 0,icon="icon-insert_drive_file")
public class MyOrderController {
	
	@MenuTag(code = "myOrder-soft-history", name = "软装派单", sequence = 1, type = 1 )
	@RequestMapping(value = "soft-history")
	public void index1(){
		
	}
	
	@MenuTag(code = "myOrder-hard-history", name = "硬装派单", sequence = 2, type = 1 )
	@RequestMapping(value = "hard-history")
	public void index2(){
		
	}
}
