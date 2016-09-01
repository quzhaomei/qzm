package com.sf.qzm.interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sf.qzm.annotation.LoginTag;
import com.sf.qzm.annotation.MenuTag;
import com.sf.qzm.bean.login.LoginOutMessage;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.dto.admin.AdminUserDTO;
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
		Method method=null;
		boolean isLimit=true;
		Object bean=null;
		if (handler instanceof HandlerMethod) {// 此处handler为处理该请求的method对象
			method=((HandlerMethod) handler).getMethod();
			bean = ((HandlerMethod) handler).getBean();// 转化为该方法的class对象
		}

		if (
				(bean.getClass().getAnnotation(LoginTag.class) != null
				||bean.getClass().getAnnotation(MenuTag.class)!=null)
				&&isLimit
				) {
				AdminUserDTO adminUser = (AdminUserDTO) request.getSession().getAttribute(Constant.ADMIN_USER_SESSION);
				//登陆拦截。
				if(adminUser==null){
					return processUnLogin(request, response);
				}
				//权限拦截
				MenuTag menu=method.getAnnotation(MenuTag.class);
				if(menu!=null){
				String godPhone = SfContextUtils.getWebXmlParam(request, "godPhone");
				if (
						(adminUser.getPhone() != null && adminUser.getPhone().equals(godPhone))
						||
						(adminUser.getMenuCodeMap().get(menu.code())!=null) ){
							return true;
						}else{
							 return processUnUrlLimit(request, response);
						}
				}
				
					
		}
		//处理是否有被挤下线的情况
		LoginOutMessage loginOut= (LoginOutMessage) request.getSession().getAttribute(Constant.DESTORY_SESSION);
		if(loginOut!=null&&loginOut.getType()==0){//被登出
			return processForceLoginOut(request, response,loginOut);
		}
		return true;
	}
	
	

	/**
	 * 处理强制下线的情况
	 * @param request http请求
	 * @param response http响应
	 * @return false
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private boolean processForceLoginOut(HttpServletRequest request, HttpServletResponse response,LoginOutMessage
			message)
			throws UnsupportedEncodingException, IOException {
		String requestType = request.getHeader("X-Requested-With");
		if (requestType != null) {
			// ajax请求
			JsonDTO json = new JsonDTO();
			json.setStatus(4).setMessage(message.getMessage());
			response.setContentType("application/json;charset=utf-8");// 设置请求
			response.getOutputStream().write(JsonUtils.object2json(json).getBytes("utf-8"));
		} else {
			String contentPath = request.getContextPath();
			if (!"".equals(contentPath)) {
				response.sendRedirect(contentPath + "/adminLogin/login.htmls");// 跳转登录页面
			} else {
				response.sendRedirect("adminLogin/login.htmls");
			}
		}
		request.getSession().invalidate();//清除session

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
			request.getSession().setAttribute(Constant.LOGIN_REDIRECT_URL, orignUrl);
			// 跳转登录页面
			if (!"".equals(contentPath)) {
				response.sendRedirect(contentPath + "/adminLogin/login.htmls");// 跳转登录页面
			} else {
				response.sendRedirect("adminLogin/login.htmls");
			}
		}
		return false;
	}
}
