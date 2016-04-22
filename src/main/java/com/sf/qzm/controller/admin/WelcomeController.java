package com.sf.qzm.controller.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.qzm.annotation.ClassLimit;
import com.sf.qzm.bean.admin.AdminUser;
import com.sf.qzm.controller.BaseController;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.dto.admin.AdminUserDTO;
import com.sf.qzm.dto.admin.MenuManagerDTO;
import com.sf.qzm.service.AdminUserService;
import com.sf.qzm.service.MenuManagerService;
import com.sf.qzm.util.context.SfContextUtils;
import com.sf.qzm.util.other.PasswordUtils;

@Controller
@RequestMapping(value = "welcome")
@ClassLimit
public class WelcomeController extends BaseController {
	@Resource
	private AdminUserService adminUserService;
	@Resource
	private MenuManagerService menuManagerService;
	// 登陆后跳转的页面
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		return "admin/welcome";
	}

	// 更改密码
	// 登陆后跳转的页面
	@RequestMapping(value = "/updatePsw")
	@ResponseBody
	public JsonDTO updatePsw(HttpServletRequest request,
			String oldPsw,String newPsw) {
		AdminUserDTO loginUser = getLoginAdminUser(request);
		JsonDTO json = new JsonDTO();
		if (oldPsw != null && newPsw != null && oldPsw.length() <= 50
				&& newPsw.length() <= 50) {
			AdminUser adminUser = new AdminUser();
			adminUser.setAdminUserId(loginUser.getAdminUserId());
			adminUser.setPassword(PasswordUtils.MD5(oldPsw));
			loginUser = adminUserService.getByParam(adminUser);
			if (loginUser != null) {// 验证成功，修改密码
				adminUser.setPassword(PasswordUtils.MD5(newPsw));
				try {
					adminUserService.saveOrUpdate(adminUser);
					json.setStatus(1).setMessage("密码更新成功！");
				} catch (Exception e) {
					e.printStackTrace();
					json.setStatus(0).setMessage("密码更新失败！：" + e.getMessage());
				}
				return json;
			}
		}
		json.setStatus(0).setMessage("密码错误！");
		return json;
	}

	// 负责菜单的加载
	@RequestMapping(value = "/menus")
	public String getMenus(HttpServletRequest request,
			Model model) {
		// 从session中获取所有的角色信息
		AdminUserDTO userDTO = getLoginAdminUser(request);
		Map<String, MenuManagerDTO> menusMap = userDTO.getMenuList();
		if (menusMap != null) {
			List<MenuManagerDTO> menus = null;

			String godPhone = SfContextUtils.getWebXmlParam(request, "godPhone");
			if (userDTO.getPhone()!=null&&userDTO.getPhone().equals(godPhone)) {
				menus = menuManagerService.getNavMenu();// 获取所有的菜单组或者页面菜单，
			} else {
				menus = new ArrayList<MenuManagerDTO>();
				menus.addAll(menusMap.values());
			}
			// 根据ID排序
			Collections.sort(menus, new Comparator<MenuManagerDTO>() {
				@Override
				public int compare(MenuManagerDTO menu1, MenuManagerDTO menu2) {
					return menu1.getMenuId() - menu2.getMenuId();
				}
			});

			model.addAttribute("pageMenus", menus);
		}
		return "admin/menu";
	}
	
}
