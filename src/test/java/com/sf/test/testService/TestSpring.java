package com.sf.test.testService;

import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sf.qzm.bean.admin.AdminUser;
import com.sf.qzm.service.AdminUserService;
import com.sf.qzm.util.context.SfContextUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/resources/spring-mybatis.xml",
		"file:src/test/resources/spring-context.xml" })
public class TestSpring extends AbstractJUnit4SpringContextTests {
	@Resource
	private AdminUserService adminUserService;
	@Resource
	private SfContextUtils sfContextUtils;
}
