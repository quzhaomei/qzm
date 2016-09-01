package com.sf.qzm.controller.admin;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.qzm.bean.bis.Cooperation;
import com.sf.qzm.controller.BaseController;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.service.CooperationService;
import com.sf.qzm.util.other.Constant;

public class CooperationController extends BaseController {
	@Resource
	private CooperationService cooperationService;
	@RequestMapping("/index")
	public String index(Model model){
		model.addAttribute("cooperations", cooperationService.getListByParam(new Cooperation()));
		return "admin/cooperation";
	}
	@RequestMapping("/add")
	@ResponseBody
	public JsonDTO add(Cooperation cooperation){
		JsonDTO json=new JsonDTO();
		if(cooperationService.getByParam(cooperation)!=null){
			json.setStatus(0).setMessage("code已经存在了");
		}else{
			cooperation.setCreateDate(new Date());
			try {
				cooperationService.saveOrUpdate(cooperation);
				json.setStatus(1).setMessage("创建成功!");
			} catch (Exception e) {
				json.setStatus(0).setMessage("创建时，系统发生异常:"+e.getMessage());
				e.printStackTrace();
			}
		}
		return json;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public JsonDTO add(Cooperation cooperation,String operator){
		JsonDTO json=new JsonDTO();
		if(Constant.FIND_BY_ID.equals(operator)){
			if(cooperationService.getById(cooperation.getCooperationId())!=null){
				json.setStatus(1).setData(cooperationService.getByParam(cooperation));
			}else{
				json.setStatus(0).setMessage("数据不存在");
			}
		}else if(Constant.EDIT.equals(operator)){
			if(cooperationService.getById(cooperation.getCooperationId())!=null){
				try {
					cooperationService.saveOrUpdate(cooperation);
					json.setStatus(1).setMessage("更新成功");
				} catch (Exception e) {
					json.setStatus(0).setMessage("更新失败");
					e.printStackTrace();
				}
			}else{
				json.setStatus(0).setMessage("数据不存在");
			}
		}
		return json;
	}
}
