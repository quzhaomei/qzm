package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.login.LoginHistory;

public interface LoginHistoryDao {
	void save(@Param(value="history") LoginHistory history);
	LoginHistory getByParam(@Param(value="history") LoginHistory history);
	List<LoginHistory> list(@Param(value="history") LoginHistory history);
	void update(@Param(value="history") LoginHistory history);
}
