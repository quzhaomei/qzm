package com.sf.qzm.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sf.qzm.bean.login.LoginCookie;
import com.sf.qzm.dao.LoginCookieDao;
import com.sf.qzm.service.LoginCookieService;

@Service
@Transactional
public class LoginCookieServiceImpl implements LoginCookieService {
	
	@Resource
	private LoginCookieDao cookieDao;
	
	@Override
	public void save(LoginCookie cookie) {
		String loginname=cookie.getLoginname();
		String ip=cookie.getIp();
		LoginCookie old=cookieDao.getCookie(loginname,ip);
		if(old!=null){
			cookieDao.delete(loginname, ip);
		}
		cookieDao.save(cookie);
	}

	@Override
	public void delete(String loginname, String ip) {
		cookieDao.delete(loginname, ip);
	}

	@Override
	public LoginCookie getCookie(String loginname, String ip) {
		return cookieDao.getCookie(loginname, ip);
	}

}
