package com.sf.qzm.interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sf.qzm.annotation.ClassLimit;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.dto.admin.AdminUserDTO;
import com.sf.qzm.dto.admin.MenuManagerDTO;
import com.sf.qzm.util.context.SfContextUtils;
import com.sf.qzm.util.other.Constant;
import com.sf.qzm.util.other.JsonUtils;

/**
 * 权限拦截
 */
public class LimitInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {// 此处handler为处理该请求的method对象
			handler = ((HandlerMethod) handler).getBean();// 转化为该方法的class对象
		}
		// 页面uri绑定
		// request.setAttribute("pageUri", request.getRequestURI());

		if (handler.getClass().getAnnotation(ClassLimit.class) != null) {
			ClassLimit tag = handler.getClass().getAnnotation(ClassLimit.class);
			boolean adminLogin = tag.adminLogin();// 拦截后台登录
			boolean uri = tag.uri();// 拦截uri
			if (adminLogin) {// 拦截后台登录
				AdminUserDTO adminUser = (AdminUserDTO) request.getSession().getAttribute(Constant.ADMIN_USER_SESSION);
				if (adminUser != null) {
					String godPhone = SfContextUtils.getWebXmlParam(request, "godPhone");
					if (!uri || (adminUser.getPhone() != null && adminUser.getPhone().equals(godPhone))) {
						return true;
					} else {
						// 得到绝对路径
						String servletPath=request.getServletPath();
						Map<String, MenuManagerDTO> menus = adminUser.getMenuList();
						if (isInnerDispatcher(servletPath)||(menus != null && menus.get(servletPath) != null)) {
							return true;
						} else {
							return processUnUrlLimit(request, response);
						}
					}
				} else {
					// 没有登录
					return processUnLogin(request, response);
				}
			}
		}
		return true;
	}
	
	/**
	 * 判断是否是框架内部转发
	 * @param servletPath 请求的servletpath
	 * @return 如果是内部，则返回false；
	 */
	private boolean isInnerDispatcher(String servletPath) {
		if(servletPath.indexOf("/WEB-INF/")==0){
			return true;
		}
		return false;
	}

	/**
	 * 处理没有url权限的请求
	 * @param request http请求
	 * @param response http响应
	 * @return false
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private boolean processUnUrlLimit(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, IOException {
		// 没有权限。
		String requestType = request.getHeader("X-Requested-With");
		if (requestType != null) {
			// ajax请求
			JsonDTO json = new JsonDTO();
			json.setStatus(2).setMessage("无权限");
			response.setContentType("application/json;charset=utf-8");// 设置请求
			response.getOutputStream().write(JsonUtils.object2json(json).getBytes("utf-8"));
		} else {
			String contentPath = request.getContextPath();
			if (!"".equals(contentPath)) {
				response.sendRedirect(contentPath + "/welcome/index.htmls");// 首页
			} else {
				response.sendRedirect("/welcome/index.htmls");// 首页
			}
		}

		return false;
	}

	/**
	 * 处理没有登录的情况， 如果是ajax 则提示未登录， 如果是普通请求，则返回登录页面，并附带原请求链接 param=orignUrl 返回
	 * false
	 * @param request http请求
	 * @param response http回应
	 * @return false
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private boolean processUnLogin(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, IOException {
		String requestType = request.getHeader("X-Requested-With");
		if (requestType != null) {// 如果是ajax请求
			JsonDTO json = new JsonDTO();
			json.setStatus(3).setMessage("请先登录");
			response.setContentType("application/json;charset=utf-8");// 设置请求
			response.getOutputStream().write(JsonUtils.object2json(json).getBytes("utf-8"));
		} else {
			String orignUrl = request.getServletPath();// 原请求url
			String contentPath = request.getContextPath();
			// 跳转登录页面
			if (!"".equals(contentPath)) {
				response.sendRedirect(contentPath + "/login.htmls?orignUrl=" + URLEncoder.encode(orignUrl, "utf-8"));// 跳转登录页面
			} else {
				response.sendRedirect("login.htmls?orignUrl=" + URLEncoder.encode(orignUrl, "utf-8"));
			}
		}
		return false;
	}
}
