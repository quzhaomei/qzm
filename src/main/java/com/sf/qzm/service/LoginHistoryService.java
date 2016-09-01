package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.login.LoginHistory;

public interface LoginHistoryService {
	void save(LoginHistory history);
	void update(LoginHistory history);
	LoginHistory get(String sessionId);
	List<LoginHistory> get(Integer adminUserId,Integer status);
}
