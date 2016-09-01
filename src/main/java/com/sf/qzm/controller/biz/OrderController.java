package com.sf.qzm.controller.biz;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sf.qzm.annotation.MenuTag;

@Controller
@RequestMapping("order")
@MenuTag(code = "order", name = "订单结算系统", sequence = 6, type = 0,icon="icon-coin-yen")
public class OrderController {
	
	@MenuTag(code = "order-history", name = "账户结算管理", sequence = 1, type = 1 )
	@RequestMapping(value = "order-history")
	public void index1(){
		
	}
	@MenuTag(code = "order-all", name = "全部订单", sequence = 2, type = 1 )
	@RequestMapping(value = "order-all")
	public void index2(){
		
	}
}
