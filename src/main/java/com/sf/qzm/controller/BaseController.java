package com.sf.qzm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import com.sf.qzm.dto.admin.AdminUserDTO;
import com.sf.qzm.socket.entity.UserInfo;
import com.sf.qzm.util.other.Constant;

/**
 * 基础controller，封装一系列公共方法
 * @author qzm
 * @since 2015-5-13
 */
@Controller
public class BaseController {
	/**
	 *  将登录用户 绑定在session中
	 * @param request 当前请求的request
	 * @param user 当前登录的用户信息
	 */
	protected void setLoginAdminUser(HttpServletRequest request,AdminUserDTO user){
		request.getSession().setAttribute(Constant.ADMIN_USER_SESSION, user);
	}
	
	
	/**
	 * 获取当前登录的对象
	 * @param request 当前请求的request
	 * @return 返回当前登录的用户对象
	 */
	protected AdminUserDTO getLoginAdminUser(HttpServletRequest request){
		return (AdminUserDTO) request.getSession().getAttribute(Constant.ADMIN_USER_SESSION);
	}
	
	
	
	protected void setFromUser(HttpServletRequest request,UserInfo user){
		request.getSession().setAttribute(Constant.FROM_USER, user);
	}
	protected void setToUser(HttpServletRequest request,UserInfo user){
		request.getSession().setAttribute(Constant.TO_USER, user);
	}
	
	protected UserInfo getFromUser(HttpServletRequest request){
		return (UserInfo) request.getSession().getAttribute(Constant.FROM_USER);
	}
	protected UserInfo getToUser(HttpServletRequest request){
		return (UserInfo) request.getSession().getAttribute(Constant.TO_USER);
	}
}
