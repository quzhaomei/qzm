package com.sf.test.testService;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sf.qzm.bean.login.LoginCookie;
import com.sf.qzm.dao.LoginCookieDao;
import com.sf.qzm.dao.SystemSourceDao;
import com.sf.qzm.util.context.SfContextUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/resources/spring-mybatis.xml",
		"file:src/test/resources/spring-context.xml" })
public class TestSpring extends AbstractJUnit4SpringContextTests {
	@Value("#{propertyConfigurer['jdbc.username']}")
    private String url;
	@Test
	public void test(){
		System.out.println(SfContextUtils.getComponent(SystemSourceDao.class).executeSql("select @@basedir as basePath from dual  "));
	}
	@Test
	public void test1(){
		LoginCookie cookie=new LoginCookie();
		cookie.setCreateDate(new Date());
		cookie.setIp("xxxx");
		cookie.setLoginname("xsadad");
		SfContextUtils.getComponent(LoginCookieDao.class).save(cookie);
	}
}
