package com.sf.qzm.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sf.qzm.bean.admin.AdminUser;
import com.sf.qzm.controller.BaseController;
import com.sf.qzm.dto.admin.AdminUserDTO;
import com.sf.qzm.dto.admin.MenuManagerDTO;
import com.sf.qzm.dto.admin.RoleManagerDTO;
import com.sf.qzm.service.AdminUserService;
import com.sf.qzm.service.RoleToMenusService;
import com.sf.qzm.util.other.PasswordUtils;

@Controller
@RequestMapping(value = "/")
public class AdminLoginController extends BaseController {
	@Resource
	private AdminUserService adminUserService;
	@Resource
	private RoleToMenusService roleToMenusService;

	/**
	 * 跳转登录界面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String login(AdminUser adminUser, HttpServletRequest request, Map<String, String> map
			, String orignUrl) {
		// 检测是否已经登录，登录过，则直接跳转欢迎页面
		AdminUserDTO loginUser = getLoginAdminUser(request);
		if (loginUser != null) {
			return "redirect:welcome/index.htmls";
		}
		if(adminUser.getLoginname()==null){
			map.put("info", "请输入用户名");
			return "admin/adminLogin";
		}else if(adminUser.getPassword()==null){
			map.put("info", "请输入密码");
			return "admin/adminLogin";
		}
		adminUser.setType(AdminUser.TYPE_ADMIN);// 管理账号
		//密码进行md5加密
		adminUser.setPassword(PasswordUtils.MD5(adminUser.getPassword()));
		loginUser = adminUserService.getByParam(adminUser);
		if (loginUser == null) {
			map.put("info", "用户名或密码错误！");
		} else if (loginUser.getStatus() != 1) {
			map.put("info", "该账号已被冻结");
		} else {
			// 获取用户的角色
			List<RoleManagerDTO> roles = loginUser.getRoles();
			// 处理角色菜单
			if (roles != null) {
				Map<String, MenuManagerDTO> menuList = new HashMap<String, MenuManagerDTO>();
				for (RoleManagerDTO role : roles) {
					Integer roleId = role.getRoleId();
					List<MenuManagerDTO> menus = roleToMenusService.getUseFullMenusByRoleId(roleId);
					for (MenuManagerDTO menu : menus) {
						// 处理uri menu/index.html类似
						String uri = menu.getUri();
						if (uri.matches("/\\w+/\\w+\\.htmls")) {
							menuList.put(uri, menu);
						} else {
							menuList.put(uri + menu.getMenuId(), menu);
						}
					}
				}
				loginUser.setMenuList(menuList);
			}
			setLoginAdminUser(request, loginUser);
			if(orignUrl!=null&&!"".endsWith(orignUrl)){
				return "redirect:"+orignUrl;
			}else{
				return "redirect:welcome/index.htmls";
			}
		}
		return "admin/adminLogin";
	}

	// 安全退出
	@RequestMapping(value = "/loginout")
	public String loginout(HttpSession session) {
		session.invalidate();
		return "admin/adminLogin";
	}
}
