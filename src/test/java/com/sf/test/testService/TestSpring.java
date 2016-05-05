package com.sf.test.testService;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sf.qzm.bean.bis.TalkHistory;
import com.sf.qzm.dao.TalkHistoryDao;
import com.sf.qzm.util.context.SfContextUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/resources/spring-mybatis.xml",
		"file:src/test/resources/spring-context.xml" })
public class TestSpring extends AbstractJUnit4SpringContextTests {
	@Test
	public void test(){
		TalkHistory history=new TalkHistory();
		history.setCreateDate(new Date().getTime());
		history.setFromId("fromId");
		history.setToId("toId");
		history.setFromName("fromName");
		history.setToName("toName");
		history.setFromHeadImg("//dsads");
		history.setMessage("hahaha");
		history.setToHeadImg("/asdsd");
		history.setStatus(TalkHistory.STATUS_UN_ACCEPT);
		SfContextUtils.getComponent(TalkHistoryDao.class).save(history);
	}
}
