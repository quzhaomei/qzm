package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.biz.AccountHistory;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.biz.AccountHistoryDTO;

public interface AccountHistoryDao {
	void save(@Param(value="history") AccountHistory history);
	void update(@Param(value="history")AccountHistory history);
	
	AccountHistoryDTO get(@Param(value="history")AccountHistory history);
	List<AccountHistoryDTO> list(@Param(value="page")PageDTO<AccountHistory> page);
	int count(@Param(value="history")AccountHistory history);
}
