package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.biz.AccountHistory;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.biz.AccountHistoryDTO;

public interface AccountHistoryService {
	void saveCompanyAccount(Integer companyId, Integer price,Integer createUserId,String comment);
	
	void save(AccountHistory accountHistory);
	PageDTO<List<AccountHistoryDTO>> getHistory(PageDTO<AccountHistory> page);
}
