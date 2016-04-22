package com.sf.qzm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import com.sf.qzm.dto.admin.AdminUserDTO;
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
	
	
	/**
	 * 例如 http://www.exasd.cn/{contentPath}/log.html. 
	 * 最后处理返回 <p>http://www.exasd.cn/{contentPath}</p>
	 * <b>contentPath</b> 为当前项目的上下文路径，没有则为空格
	 * <br/>
	 * @param request 当前请求的request
	 * @return http 请求的根路径
	 */
	protected String getRootUrl(HttpServletRequest request){
		String host=request.getRequestURL().toString();
		while(host.lastIndexOf("/")>7){
			host=host.substring(0,host.lastIndexOf("/"));
		}
		String contextPath=request.getContextPath();
		if(!"".equals(contextPath)){
			host=host+contextPath;
		}
		return host;
	}
	
}
