package com.sf.qzm.controller.biz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.qzm.annotation.MenuTag;
import com.sf.qzm.bean.constant.Age;
import com.sf.qzm.bean.constant.Budget;
import com.sf.qzm.bean.constant.HouseStyle;
import com.sf.qzm.controller.BaseController;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.service.AgeService;
import com.sf.qzm.service.BudgetService;
import com.sf.qzm.service.HouseStyleService;
import com.sf.qzm.util.other.Constant;
import com.sf.qzm.util.other.JsonUtils;

@Controller
@RequestMapping("constant")
@MenuTag(code = "constant", name = "系统常量管理", sequence = 10, type = 0,icon="icon-cog")
public class ConstantController extends BaseController {
	
	/*** 年龄段开始*/
	@Resource
	private AgeService ageService;
	@MenuTag(code = "constant-age-index", name = "年龄段管理", sequence = 1, type = 1 )
	@RequestMapping(value = "/age-index")
	public String  ageIndex(String operator,Map<String,Object> map){
		if(Constant.LIST.equals(operator)){
			List<Age> ages=ageService.all();
			map.put(Constant.JSON, JsonUtils.object2json(ages));
			return Constant.JSON;
		}
		return "admin/age-list";
	}
	
	@MenuTag(code = "constant-age-save", name = "添加年龄段", sequence = 1, type = 2,parentCode="constant-age-index" )
	@RequestMapping(value = "/age-save")
	@ResponseBody
	public JsonDTO  ageSave(Age age){
		JsonDTO json=new JsonDTO();
		try {
			ageService.saveOrUpdate(age);
			json.setStatus(1).setMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setStatus(0).setMessage("保存过程中，系统出现异常");
		}
		return json;
	}
	
	@MenuTag(code = "constant-age-edit", name = "修改年龄段", sequence = 2, type = 2,parentCode="constant-age-index" )
	@RequestMapping(value = "/age-edit")
	@ResponseBody
	public JsonDTO  ageedit(Age age){
		JsonDTO json=new JsonDTO();
		try {
			ageService.saveOrUpdate(age);
			json.setStatus(1).setMessage("修改成功！");
		} catch (Exception e) {
			json.setStatus(0).setMessage("修改过程中，系统出现异常");
		}
		return json;
	}
	
	@MenuTag(code = "constant-age-delete", name = "修改年龄段", sequence = 2, type = 2,parentCode="constant-age-index" )
	@RequestMapping(value = "{ageId}/age-delete")
	@ResponseBody
	public JsonDTO  ageDelete(@PathVariable("ageId") Integer ageId){
		JsonDTO json=new JsonDTO();
		try {
			ageService.delete(ageId);
			json.setStatus(1).setMessage("删除成功！");
		} catch (Exception e) {
			json.setStatus(0).setMessage("删除过程中，系统出现异常");
		}
		return json;
	}
	/*** 年龄段结束*/
	
	
	/** 预算开始*/
	@Resource
	private BudgetService budgetService;
	@MenuTag(code = "constant-budget-index", name = "预算管理", sequence = 2, type = 1 )
	@RequestMapping(value = "/budget-index")
	public String  budgetIndex(String operator,Map<String,Object> map){
		if(Constant.LIST.equals(operator)){
			List<Budget> budget=budgetService.all();
			map.put(Constant.JSON, JsonUtils.object2json(budget));
			return Constant.JSON;
		}
		return "admin/budget-list";
	}
	
	@MenuTag(code = "constant-budget-save", name = "添加预算", sequence = 1, type = 2,parentCode="constant-budget-index" )
	@RequestMapping(value = "/budget-save")
	@ResponseBody
	public JsonDTO  budgetSave(Budget budget){
		JsonDTO json=new JsonDTO();
		try {
			budgetService.saveOrUpdate(budget);
			json.setStatus(1).setMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setStatus(0).setMessage("保存过程中，系统出现异常");
		}
		return json;
	}
	
	@MenuTag(code = "constant-budget-edit", name = "修改预算", sequence = 2, type = 2,parentCode="constant-budget-index" )
	@RequestMapping(value = "/budget-edit")
	@ResponseBody
	public JsonDTO  budgetEdit(Budget budget){
		JsonDTO json=new JsonDTO();
		try {
			budgetService.saveOrUpdate(budget);
			json.setStatus(1).setMessage("修改成功！");
		} catch (Exception e) {
			json.setStatus(0).setMessage("修改过程中，系统出现异常");
		}
		return json;
	}
	
	@MenuTag(code = "constant-budget-delete", name = "删除预算", sequence = 2, type = 2,parentCode="constant-budget-index" )
	@RequestMapping(value = "{budgetId}/age-delete")
	@ResponseBody
	public JsonDTO  budgetDelete(@PathVariable("budgetId") Integer budgetId){
		JsonDTO json=new JsonDTO();
		try {
			budgetService.delete(budgetId);
			json.setStatus(1).setMessage("删除成功！");
		} catch (Exception e) {
			json.setStatus(0).setMessage("删除过程中，系统出现异常");
		}
		return json;
	}
	/** 预算结束*/
	
	
	
	/**房型开始*/
	@Resource
	private HouseStyleService styleService;
	@MenuTag(code = "constant-style-index", name = "房型管理", sequence = 2, type = 1 )
	@RequestMapping(value = "/style-index")
	public String  styleIndex(String operator,Map<String,Object> map){
		if(Constant.LIST.equals(operator)){
			List<HouseStyle> budget=styleService.all();
			map.put(Constant.JSON, JsonUtils.object2json(budget));
			return Constant.JSON;
		}
		return "admin/house-style-list";
	}
	
	@MenuTag(code = "constant-style-save", name = "添加房型", sequence = 1, type = 2,parentCode="constant-style-index" )
	@RequestMapping(value = "/style-save")
	@ResponseBody
	public JsonDTO  styleSave(HouseStyle houseStyle){
		JsonDTO json=new JsonDTO();
		try {
			styleService.saveOrUpdate(houseStyle);
			json.setStatus(1).setMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setStatus(0).setMessage("保存过程中，系统出现异常");
		}
		return json;
	}
	
	@MenuTag(code = "constant-style-edit", name = "修改房型", sequence = 2, type = 2,parentCode="constant-style-index" )
	@RequestMapping(value = "/style-edit")
	@ResponseBody
	public JsonDTO  styleEdit(HouseStyle houseStyle){
		JsonDTO json=new JsonDTO();
		try {
			styleService.saveOrUpdate(houseStyle);
			json.setStatus(1).setMessage("修改成功！");
		} catch (Exception e) {
			json.setStatus(0).setMessage("修改过程中，系统出现异常");
		}
		return json;
	}
	
	@MenuTag(code = "constant-style-delete", name = "删除房型", sequence = 3, type = 2,parentCode="constant-style-index" )
	@RequestMapping(value = "{styleId}/style-delete")
	@ResponseBody
	public JsonDTO  styleDelete(@PathVariable("styleId") Integer styleId){
		JsonDTO json=new JsonDTO();
		try {
			styleService.delete(styleId);
			json.setStatus(1).setMessage("删除成功！");
		} catch (Exception e) {
			json.setStatus(0).setMessage("删除过程中，系统出现异常");
		}
		return json;
	}
	/**房型结束*/
	
	
}
