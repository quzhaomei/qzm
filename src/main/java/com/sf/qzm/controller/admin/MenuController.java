package com.sf.qzm.controller.admin;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.qzm.annotation.ClassLimit;
import com.sf.qzm.bean.admin.MenuManager;
import com.sf.qzm.controller.BaseController;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.dto.admin.MenuManagerDTO;
import com.sf.qzm.service.MenuManagerService;
import com.sf.qzm.util.other.Constant;
import com.sf.qzm.util.other.JsonUtils;

@Controller
@RequestMapping(value = "menu")
@ClassLimit(uri = true)
public class MenuController extends BaseController {
	@Resource
	private MenuManagerService menuManagerService;

	// 负责跳转菜单页面
	@RequestMapping(value = "/index")
	public String index(Model model, String operator) {
		if (Constant.LIST.equals(operator)) {// 获取所有
			List<MenuManagerDTO> menus = menuManagerService.getAllMenu();// 获取所有的menu
			model.addAttribute("json", JsonUtils.object2json(menus));
			return Constant.JSON;
		}
		return "admin/menuManager";
	}

	// 负责所有的查询逻辑以及ajax
	@RequestMapping(value = "/list")
	@ResponseBody
	public JsonDTO list(Integer menuId, String operator) {
		JsonDTO json = new JsonDTO();
		if (Constant.FIND_BY_ID.equals(operator)) {// 获取当个
			json.setStatus(1).setData(menuManagerService.getById(menuId));
		} else {
			json.setStatus(0).setMessage("参数异常");
		}
		return json;
	}

	//
	private boolean menuOkCheck(MenuManager saveParam) {
		Integer parentId = saveParam.getParentId();
		Integer typeInt = saveParam.getType();
		if (parentId == -1 && (typeInt == 0 || typeInt == 1)) {
			return true;
		} else if (parentId != -1 && (typeInt == 1 || typeInt == 2)) {
			MenuManagerDTO menuDTO = menuManagerService.getById(parentId);
			if (menuDTO != null) {
				Integer menuType = menuDTO.getType();
				return (menuType == 0 && typeInt == 1 || menuType == 1 && typeInt == 2);
			}
		}
		return false;
	}

	// 增加菜单
	@RequestMapping(value = "/add")
	@ResponseBody
	public JsonDTO add(MenuManager saveParam) {
		JsonDTO json = new JsonDTO();
		if (menuOkCheck(saveParam)) {
			saveParam.setCreateDate(new Date());
			saveParam.setStatus(MenuManager.STATUS_ACTIVE);
			try {
				menuManagerService.saveOrUpdate(saveParam);
				json.setStatus(1).setMessage("插入菜单成功");
			} catch (Exception e) {
				e.printStackTrace();
				json.setStatus(0)// 操作失败
						.setMessage("插入菜单时，系统异常:" + e.getMessage());
			}
			return json;
		}
		return json;
	}

	// 更新菜单
	@RequestMapping(value = "/update")
	@ResponseBody
	public JsonDTO update(MenuManager updateParam) {
		JsonDTO json = new JsonDTO();
		Integer menuId = updateParam.getMenuId();
		MenuManagerDTO menuDTO = menuManagerService.getById(menuId);
		if (menuDTO != null) {
			try {
				menuManagerService.saveOrUpdate(updateParam);
				json.setStatus(1)// 操作成功
						.setMessage("更新菜单成功");
			} catch (Exception e) {
				e.printStackTrace();
				json.setStatus(0)// 操作失败
						.setMessage("更新菜单时，系统异常:" + e.getMessage());
			}
		}
		return json;
	}

	// 删除菜单
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/delete")
	@ResponseBody
	public JsonDTO delete(Integer menuId, Model model) {
		JsonDTO json = new JsonDTO();
		// 当含有子菜单的时候 不允许删除。
		MenuManager selectParam = new MenuManager();
		selectParam.setParentId(menuId);
		List<MenuManagerDTO> childMenu = menuManagerService.getListByParam(selectParam);
		if (childMenu.size() != 0) {
			json.setStatus(0);// 操作失败
			json.setMessage("请先删除该菜单下的子菜单");
		} else {
			MenuManager delParam = new MenuManager();
			delParam.setMenuId(menuId);
			try {
				menuManagerService.delete(delParam);
				json.setStatus(1)// 操作成功
						.setMessage("删除菜单成功");
			} catch (Exception e) {
				e.printStackTrace();
				json.setStatus(0)// 操作失败
						.setMessage("删除该菜单时，系统异常");
			}
		}
		return json;
	}
}
