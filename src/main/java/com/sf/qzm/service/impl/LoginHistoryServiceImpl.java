package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.login.LoginHistory;
import com.sf.qzm.dao.LoginHistoryDao;
import com.sf.qzm.service.LoginHistoryService;

@Service
@Transactional
public class LoginHistoryServiceImpl implements LoginHistoryService {
	
	@Resource
	private LoginHistoryDao historyDao;
	@Override
	public void save(LoginHistory history) {
		historyDao.save(history);
	}

	@Override
	public void update(LoginHistory history) {
		historyDao.update(history);
	}

	@Override
	public LoginHistory get(String sessionId) {
		LoginHistory history=new LoginHistory();
		history.setSessionId(sessionId);
		return historyDao.getByParam(history);
	}

	@Override
	public List<LoginHistory> get(Integer adminUserId, Integer status) {
		LoginHistory history=new LoginHistory();
		history.setAdminUserId(adminUserId);
		history.setStatus(status);
		return historyDao.list(history);
	}

}
