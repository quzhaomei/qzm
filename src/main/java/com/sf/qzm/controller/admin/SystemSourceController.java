package com.sf.qzm.controller.admin;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.qzm.annotation.ClassLimit;
import com.sf.qzm.bean.bis.SystemSource;
import com.sf.qzm.controller.BaseController;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.dto.bis.SystemSourceDTO;
import com.sf.qzm.service.SystemSourceService;
import com.sf.qzm.util.other.Constant;

@Controller
@RequestMapping("source")
@ClassLimit(uri=true)
public class SystemSourceController extends BaseController {
	@Resource
	private SystemSourceService sourceService;
	@RequestMapping("/index")
	public String index(Model model){
		model.addAttribute("sources", sourceService.getListByParam(new SystemSource()));
		return "admin/source";
	}
	@RequestMapping("/add")
	@ResponseBody
	public JsonDTO add(SystemSource systemSource){
		JsonDTO json=new JsonDTO();
		if(sourceService.getByParam(systemSource)!=null){
			json.setStatus(0).setMessage("key已经存在了");
		}else{
			systemSource.setCreateDate(new Date());
			try {
				sourceService.saveOrUpdate(systemSource);
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
	public JsonDTO add(SystemSource source,String operator){
		JsonDTO json=new JsonDTO();
		if(Constant.FIND_BY_ID.equals(operator)){
			SystemSourceDTO sourceDate=sourceService.getByParam(source);
			if(sourceDate!=null){
				json.setStatus(1).setData(sourceDate);
			}else{
				json.setStatus(0).setMessage("数据不存在");
			}
		}else if(Constant.EDIT.equals(operator)){
			SystemSourceDTO sourceDate=sourceService.getByParam(source);
			if(sourceDate!=null){
				try {
					sourceService.saveOrUpdate(source);
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
