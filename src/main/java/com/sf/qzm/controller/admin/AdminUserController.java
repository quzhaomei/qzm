package com.sf.qzm.controller.admin;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.admin.AdminUserDTO;
import com.sf.qzm.dto.admin.RoleManagerDTO;
import com.sf.qzm.service.AdminUserService;
import com.sf.qzm.service.RoleManagerService;
import com.sf.qzm.util.other.Constant;
import com.sf.qzm.util.other.PasswordUtils;


@Controller
@RequestMapping(value = "user")
@ClassLimit(uri = true)
public class AdminUserController extends BaseController {
	@Resource
	private AdminUserService adminUserService;
	
	@Resource
	private RoleManagerService roleManagerService;
	
	// 负责跳转菜单页面,并查出用户列表
	@RequestMapping(value = "/index")
	public String index(PageDTO<AdminUser> page,Model model,
			Integer type) {
			AdminUser selectParam = new AdminUser();
			page.setParam(selectParam);
				
			if(type!=null&&type!=99) selectParam.setType(type);

			PageDTO<List<AdminUserDTO>> pageDate = adminUserService.getPageByParam(page);
			
			// 查找所有角色
			List<RoleManagerDTO> roleList = roleManagerService.getAllRole();
			model.addAttribute("type", type);
			model.addAttribute("roles", roleList);
			model.addAttribute("pageResult", pageDate);
			return "admin/adminUser";
	}
	/**
	 * 处理验证情况
	 * @param response 响应对象
	 * @param user 请求参数
	 */
	private void produceCheck(HttpServletResponse response,AdminUser user){
		int count = adminUserService.getCountByParam(user);
		try {
			response.getWriter().print(count==0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 检测将要更新的用户数据
	 * @param user 用户参数
	 * @param require 是否每个字段都必须
	 * @return 用户数据合法 则返回true
	 */
	private boolean checkUserParam(AdminUser user,boolean require){
		String nickname = user.getNickname();
		String loginname =user.getLoginname();
		String phone = user.getPhone();
		String email =user.getEmail();
		String description = user.getDescription();
		if(require){
			if(nickname==null||loginname==null||phone==null||email==null||description==null){
				return false;
			}
		}
		if (nickname!=null&&nickname.length() > 10) {// 验证昵称，
			return false;
		}
		if (loginname!=null&&(loginname.length() > 20|| !loginname.matches("[\\d\\w]{6,20}"))) {// 验证登录名
			return false;
		} 
		if (email!=null&&email.length() > 30) {// 验证邮箱
			return false;
		} 
		if (description != null && description.length() > 200) {
			return false;
		} 
		if (phone!=null && phone.length() > 11) {
			return false;
		}
		return true;
	}
	/**
	 * 
	 * @param user 用户对象
	 * @param response 响应对象
	 * @param request 请求对象
	 * @param model 数据模型
	 * @param operator 操作类型
	 * @param roleArrIds 角色ids
	 * @return 返回请求结果
	 */
	// 添加用户
	@RequestMapping(value = "/add")
	@ResponseBody
	public JsonDTO add(AdminUser user, HttpServletResponse response,
			HttpServletRequest request,Model model,String operator) {
		if (Constant.CHECK.equals(operator)) {// 如果是验证，
			produceCheck(response, user);
			return null;
		}
		// 进行增加操作
		AdminUserDTO adminUserDTO = getLoginAdminUser(request);
		JsonDTO json = new JsonDTO();
			if (checkUserParam(user,true)) {
				user.setType(AdminUser.TYPE_ADMIN);// 后台管理账号
				user.setOrinal(AdminUser.ORINAL_PERSONAL);// 表示为个人账号

				user.setCreateDate(new Date());
				user.setCreateUserId(adminUserDTO.getAdminUserId());// 初始化创建人数
				user.setStatus(AdminUser.STATUS_ACTIVE);
				user.setPassword(PasswordUtils.MD5(user.getPhone()));//密码初始化为电话号码
				try {
					adminUserService.saveOrUpdate(user);
					json.setStatus(1).setMessage("保存用户成功");
				} catch (Exception e) {
					e.printStackTrace();
					json.setStatus(0).setMessage("保存用户失败:" + e.getMessage());
				}
			}else{
				json.setStatus(0).setMessage("数据异常");
			}
		return json;
	}
	
	
	// 用户明细
	@RequestMapping(value = "/list")
	@ResponseBody
	public JsonDTO list(Integer userId, String operator) {
		JsonDTO json = new JsonDTO();
		if (Constant.FIND_BY_ID.equals(operator)) {// 根据ID加载
			AdminUserDTO userDTO = adminUserService.getById(userId);
				json.setStatus(1).setData(userDTO);
		}else{
			json.setStatus(0).setMessage("参数错误");
		}
		return json;
	}

	// 更新用户
	@RequestMapping(value = "/update")
	@ResponseBody
	public JsonDTO update(AdminUser user,HttpServletRequest request,
			HttpServletResponse response,String operator,Integer userId) {
		AdminUserDTO adminUserDTO = getLoginAdminUser(request);
		JsonDTO json = new JsonDTO();
		if (Constant.CHECK.equals(operator)) {// 如果是验证，
			if (Constant.CHECK.equals(operator)) {// 如果是验证，
				produceCheck(response, user);
				return null;
			}
		} else if (Constant.FIND_BY_ID.equals(operator)) {// 根据ID加载
			if (userId != null ) {
				AdminUserDTO userDTO = adminUserService.getById(userId);
				json.setStatus(1).setData(userDTO);
				return json;
			}
		} else if (Constant.EDIT.equals(operator)) {// 更新
			// 数据校验
			if (userId != null ) {
				if (checkUserParam(user,false)) {
					AdminUserDTO userDTO = adminUserService.getById(userId);
					if(userDTO!=null){
						user.setAdminUserId(userId);
						user.setUpdateUserId(adminUserDTO.getAdminUserId());// 更新者
						user.setUpdateDate(new Date());
						try {
							adminUserService.saveOrUpdate(user);
							json.setStatus(1).setMessage("更新用户成功");
							// 如果更新的是自己信息，则更新session中的昵称
							if (userDTO.getAdminUserId().equals(
									adminUserDTO.getAdminUserId())) {
								setLoginAdminUser(request, adminUserService.getById(userId));
							}
						} catch (Exception e) {
							e.printStackTrace();
							json.setStatus(0).setMessage(
									"更新用户失败：" + e.getMessage());
						}
						return json;
					}
				}
			}
		}
		json.setStatus(0).setMessage("数据错误");
		return json;
	}
		
	// 切换状态
	@RequestMapping(value = "/status")
	@ResponseBody
	public JsonDTO status(AdminUser user,Integer userId) {
		JsonDTO json = new JsonDTO();
		if (userId != null) {
			AdminUserDTO userDTO = adminUserService.getById(userId);
			if (userDTO != null) {// 数据验证通过，开始更新
				user.setAdminUserId(userId);
				try {
					adminUserService.saveOrUpdate(user);
					json.setStatus(1).setMessage("更新用户成功");
				} catch (Exception e) {
					e.printStackTrace();
					json.setStatus(0).setMessage("更新用户失败：" + e.getMessage());
				}
			}
		}
		return json;
	}
	
	// 重置密码
	@RequestMapping(value = "/reset")
	@ResponseBody
	public JsonDTO reset(HttpServletRequest request,
			Integer userId) {
			JsonDTO json=new JsonDTO();
			AdminUserDTO userDTO = adminUserService.getById(userId);
			if (userDTO != null) {
				AdminUser updateParam = new AdminUser();
				updateParam.setAdminUserId(userId);
				updateParam.setPassword(PasswordUtils.MD5(userDTO.getPhone()));
				updateParam.setUpdateUserId(getLoginAdminUser(request).getAdminUserId());// 更新者
				updateParam.setUpdateDate(new Date());
				try {
					adminUserService.saveOrUpdate(updateParam);
					json.setStatus(1).setMessage("密码已经成功重置为手机号码");
				} catch (Exception e) {
					e.printStackTrace();
					json.setStatus(1).setMessage("重置失败：" + e.getMessage());
				}
				return json;
			}
		return null;
	}

	// 删除用户
	@RequestMapping(value = "/delete")
	@ResponseBody
	public JsonDTO delete(AdminUser user,HttpServletRequest request,Integer userId) {
		JsonDTO json = new JsonDTO();
		AdminUserDTO adminUserDTO = getLoginAdminUser(request);
		if (userId != null) {
			AdminUserDTO userDTO = adminUserService.getById(userId);
			if (userDTO != null) {// 数据验证通过，开始更新
				user.setAdminUserId(userId);
				user.setUpdateUserId(adminUserDTO.getAdminUserId());// 更新者
				user.setUpdateDate(new Date());
				user.setStatus(AdminUser.STATUS_DELETE);
				try {
					adminUserService.saveOrUpdate(user);
					json.setStatus(1).setMessage("删除成功");
				} catch (Exception e) {
					e.printStackTrace();
					json.setStatus(0).setMessage("删除用户失败：" + e.getMessage());
				}
			}
		}
		return json;
	}
	
}
