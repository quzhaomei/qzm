package com.sf.qzm.dao;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.login.LoginCookie;

public interface LoginCookieDao {
	void save(@Param(value="cookie") LoginCookie cookie);
	void delete(@Param(value="loginname") String loginname,@Param(value="ip") String ip);
	LoginCookie getCookie(@Param(value="loginname") String loginname,@Param(value="ip") String ip);
}
