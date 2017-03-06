package com.sf.test.testService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sf.qzm.dao.SystemSourceDao;
import com.sf.qzm.service.ActivityPeopleService;
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
		try {
			System.out.println(SfContextUtils.getComponent(ActivityPeopleService.class).getById(1));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

