package com.sf.qzm.controller.admin;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.qzm.bean.admin.RoleManager;
import com.sf.qzm.controller.BaseController;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.dto.admin.RoleManagerDTO;
import com.sf.qzm.service.RoleManagerService;
import com.sf.qzm.util.other.Constant;

/**
 * 角色管理
 * 
 * @author qzm
 * @since 2015-8-31
 */
public class RoleController extends BaseController {
	@Resource
	private RoleManagerService roleManagerService;

	// 首页查询所有角色
	@RequestMapping(value = "/index")
	public String index(Model model) {
		model.addAttribute("roles", roleManagerService.getAllRole());
		return "admin/roleManager";
	}

	// 负责所有的查询逻辑以及ajax
	@RequestMapping(value = "/list")
	@ResponseBody
	public JsonDTO list(Integer roleId, Model model, String operator) {
		JsonDTO json = new JsonDTO();
		if (Constant.FIND_BY_ID.equals(operator)) {// 获取当个
			RoleManagerDTO role = roleManagerService.getById(roleId);
			json.setStatus(1).setData(role);
		} else {
			json.setStatus(0).setMessage("参数异常");
		}
		return json;
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public JsonDTO add(RoleManager saveParam) {
		JsonDTO json = new JsonDTO();
		saveParam.setStatus(RoleManager.STATUS_ACTIVE);
		saveParam.setCreateDate(new Date());
		try {
			roleManagerService.saveOrUpdate(saveParam);
			json.setStatus(1).setMessage("保存角色成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.setStatus(0).setMessage("保存角色时，发生错误:" + e.getMessage());
		}
		return json;
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public JsonDTO update(RoleManager role, String operator) {
		JsonDTO json = new JsonDTO();
		if (Constant.FIND_BY_ID.equals(operator)) {// 获取当个
			json.setStatus(1).setData(roleManagerService.getByParam(role));
		} else {
			// 检测
			try {
				roleManagerService.saveOrUpdate(role);
				json.setStatus(1).setMessage("更新角色成功");
			} catch (Exception e) {
				e.printStackTrace();
				json.setStatus(0).setMessage("更新角色失败:" + e.getMessage());
			}
		}
		return json;
	}

	// 切换状态
	@RequestMapping(value = "/status")
	@ResponseBody
	public JsonDTO status(RoleManager role, Model model) {
		JsonDTO json = new JsonDTO();
		// 检测
		try {
			roleManagerService.saveOrUpdate(role);
			json.setStatus(1).setMessage("更新角色成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.setStatus(0).setMessage("更新角色失败:" + e.getMessage());
		}
		return json;
	}

}
