package com.sf.qzm.util.context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.sf.qzm.bean.login.LoginHistory;
import com.sf.qzm.bean.login.LoginOutMessage;
import com.sf.qzm.service.LoginHistoryService;
import com.sf.qzm.util.other.Constant;

public class LoginSessionListen implements HttpSessionListener{
	private static Map<String,HttpSession> sessionMaps=new HashMap<String, HttpSession>();
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session= se.getSession();
		sessionMaps.put(session.getId(), session);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		String sessionId=se.getSession().getId();
		LoginHistoryService historyService=SfContextUtils.getComponent(LoginHistoryService.class);
		LoginHistory history=historyService.get(sessionId);
		if(history!=null){//登出
			history.setStatus(0);
			history.setLoginoutTime(new Date());
			historyService.update(history);
		}
		sessionMaps.remove(sessionId);
	}
	
	public static void destorySession(String sessionId){
		LoginOutMessage out=new LoginOutMessage();
		out.setType(0);
		HttpSession session= sessionMaps.get(sessionId);
		if(session!=null){
			session.setAttribute(Constant.DESTORY_SESSION, out);
		}
	}
	public static void destorySession(String sessionId,String message){
		LoginOutMessage out=new LoginOutMessage();
		out.setType(0);
		out.setMessage(message);
		HttpSession session= sessionMaps.get(sessionId);
		if(session!=null){
			session.setAttribute(Constant.DESTORY_SESSION, out);
		}
	}

}
