package com.sf.qzm.controller.back;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sf.qzm.bean.login.LoginCookie;
import com.sf.qzm.bean.login.LoginHistory;
import com.sf.qzm.dto.admin.AdminUserDTO;
import com.sf.qzm.dto.menu.AutoMenuDTO;
import com.sf.qzm.service.AdminUserService;
import com.sf.qzm.service.AutoMenuService;
import com.sf.qzm.service.LoginCookieService;
import com.sf.qzm.service.LoginHistoryService;
import com.sf.qzm.util.context.LoginSessionListen;
import com.sf.qzm.util.other.Constant;
import com.sf.qzm.util.other.CookieUtils;
import com.sf.qzm.util.other.ImgUtil;
import com.sf.qzm.util.other.PasswordUtils;
import com.sf.qzm.util.other.RandomUtils;
import com.sf.qzm.util.other.StringUtils;

@Controller
@RequestMapping(value = "/adminLogin")
public class LoginController {
	
	private Integer loginLimit = 3;// 大于需要验证验证码
	public static final String TIPS_1 = "该账号正在别处登录，本机已被下线！";
	@Resource
	private AdminUserService userService;

	@Resource
	private LoginHistoryService historyService;

	@Resource
	private LoginCookieService cookieService;
	
	@Resource
	private AutoMenuService menuService;

	@RequestMapping(value = "/login")
	public String index(HttpSession session, HttpServletRequest request, 
			@RequestParam(value="username_login",required=false) String username, HttpServletResponse response,
			@RequestParam(value="password_login",required=false) String password, String loginCode, Map<String, Object> map, String remember) {
		// 如果已经登录直接跳转
		AdminUserDTO loginUser = (AdminUserDTO) session.getAttribute(Constant.ADMIN_USER_SESSION);
		// 登录状态拦截
		if (loginUser != null) {// 已经登录过
			return "forward:/welcome/index.htmls";
		} else {// 没有登录，判断登录的保存状态
			String loginname = CookieUtils.getCookie(request, Constant.LOGIN_REMMBER_TAG);
			if (loginname != null) {
				String ip = request.getRemoteAddr();
				LoginCookie cookie = cookieService.getCookie(loginname, ip);
				if (cookie != null) {
					Date create = cookie.getCreateDate();
					if ((new Date().getTime() - create.getTime()) / 1000 / 3600 / 24 > 30) {
						map.put("message", "登录状态已失效！");
						CookieUtils.deleteCookie(Constant.LOGIN_REMMBER_TAG, response);
						cookieService.delete(loginname, ip);
						return "admin/login";
					} else {
						AdminUserDTO user = userService.getUser(loginname);
						if (user != null) {// 成功登录
							session.setAttribute(Constant.ADMIN_USER_SESSION, user);
							return loginSuccess(session, request, "利用cookie登录");
						} else {// 清除cookie
							CookieUtils.deleteCookie(Constant.LOGIN_REMMBER_TAG, response);
							cookieService.delete(loginname, ip);
							return "admin/login";
						}
					}
				} else {// 删除该cookie,跳转登录
					CookieUtils.deleteCookie(Constant.LOGIN_REMMBER_TAG, response);
					return "admin/login";
				}
			}

		}

		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {// 跳转页面
			return "admin/login";
		} else {// 登录检测
			Integer loginTime = (Integer) session.getAttribute(Constant.SESSION_LOGIN_TIME);
			String message = null;
			String sessionLoginCode = (String) session.getAttribute(Constant.SESSION_LOGIN_RANDOM_CODE);
			if (loginTime != null && loginTime >= loginLimit) {// 需要验证验证码
				if (loginCode == null) {// 非空验证
					message = "请输入验证码";
					map.put("message", message);
					return "admin/login";
				} else if (!loginCode.equals(sessionLoginCode)) {// 错误验证
					message = "验证码错误";
					map.put("message", message);
					return "admin/login";
				}
			}
			AdminUserDTO user = userService.getUser(username, PasswordUtils.MD5(password));
			if (user == null) {
				message = "登录名或者密码错误！";
				loginTime = loginTime == null ? 1 : loginTime + 1;
				session.setAttribute(Constant.SESSION_LOGIN_TIME, loginTime);
			} else {// 登录成功
				if (remember != null && "yes".equals(remember)) {
					rememberCookie(user, response, request);
				}
				session.removeAttribute(Constant.SESSION_LOGIN_TIME);
				session.setAttribute(Constant.ADMIN_USER_SESSION, user);
			}

			if (message != null) {// 验证失败,跳转登录页面
				map.put("message", message);
				return "admin/login";
			}

			return loginSuccess(session, request, "利用账号密码登录");

		}

	}
	
	@RequestMapping(value = "/loginOut")
	public String loginOut(HttpServletRequest request, HttpServletResponse response, HttpSession session){//登出操作
		session.invalidate();
		//判断cookie是否存在，存在则清除
		String loginname = CookieUtils.getCookie(request, Constant.LOGIN_REMMBER_TAG);
		if(loginname!=null){
			CookieUtils.deleteCookie(Constant.LOGIN_REMMBER_TAG, response);
		}
		return "admin/login";
	}

	// 登录成功跳转
	private String loginSuccess(HttpSession session, HttpServletRequest request, String message) {
		AdminUserDTO loginUser = (AdminUserDTO) session.getAttribute(Constant.ADMIN_USER_SESSION);
		// 查找有没有同时登录的。
		List<LoginHistory> historyList = historyService.get(loginUser.getAdminUserId(), LoginHistory.STATUS_LOGIN_IN);
		if (historyList != null && historyList.size() > 0) {
			for (LoginHistory temp : historyList) {
				temp.setLoginoutTime(new Date());
				temp.setStatus(LoginHistory.STATUS_LOGIN_OUT);
				historyService.update(temp);
				LoginSessionListen.destorySession(temp.getSessionId(), TIPS_1);// 强制下线
			}
		}

		if(historyService.get(session.getId())==null){
		// 生成登录日志
			LoginHistory history = new LoginHistory();
			history.setLogininTime(new Date());
			history.setMessage(message);
			history.setIp(request.getRemoteHost());
			history.setAdminUserId(loginUser.getAdminUserId());
			history.setStatus(1);
			history.setSessionId(session.getId());
			historyService.save(history);
		}
		//绑定用户权限菜单
		List<AutoMenuDTO> menus=menuService.getAdminMenu(loginUser.getAdminUserId());//用户可以访问的菜单
		 Map<String,String> menuCodeMap=new HashMap<String, String>();
		 if(menus!=null){
			 for(AutoMenuDTO temp:menus){
				 menuCodeMap.put(temp.getCode(), "true");
			 }
		 }
		loginUser.setMenuCodeMap(menuCodeMap);
		String forwardUrl = (String) session.getAttribute(Constant.LOGIN_REDIRECT_URL);
		if (forwardUrl == null) {
			return "forward:/welcome/index.htmls";
		} else {// 跳转页面
			session.removeAttribute(Constant.LOGIN_REDIRECT_URL);
			return "forward:" + forwardUrl;
		}
	}

	private void rememberCookie(AdminUserDTO user, HttpServletResponse response, HttpServletRequest request) {
		String loginname = user.getLoginname();
		CookieUtils.saveCookie(Constant.LOGIN_REMMBER_TAG, loginname, response);

		
		String ip = request.getRemoteAddr();// 绑定ip
		
		LoginCookie oldCookie=cookieService.getCookie(loginname, ip);
		if(oldCookie!=null){//如果存在，则先删除
			cookieService.delete(loginname, ip);
		}
		// 保存本次ip与loginname
		LoginCookie loginCookie = new LoginCookie();
		loginCookie.setCreateDate(new Date());
		loginCookie.setIp(ip);
		loginCookie.setLoginname(loginname);
		cookieService.save(loginCookie);
	}

	@RequestMapping(value = "codeimg_{width}_{height}_{sessionCode}")
	public void codeimg(HttpSession session, HttpServletResponse response, @PathVariable("width") Integer width,
			@PathVariable("height") Integer height, @PathVariable("sessionCode") String sessionCode) {
		String code = RandomUtils.genertorRandomCode(4);
		session.setAttribute(sessionCode, code);

		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		BufferedImage img = null;
		try {
			img = ImgUtil.generImg(code, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			ServletOutputStream responseOutputStream = response.getOutputStream();
			ImageIO.write(img, "JPEG", responseOutputStream);
			responseOutputStream.flush();
			responseOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

}
