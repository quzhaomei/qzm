package com.sf.qzm.service;

import com.sf.qzm.bean.login.LoginCookie;

public interface LoginCookieService {
	void save(LoginCookie cookie);
	void delete(String loginname, String ip);
	LoginCookie getCookie( String loginname, String ip);
}
