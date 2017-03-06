package com.sf.qzm.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.biz.AccountHistory;
import com.sf.qzm.bean.biz.Company;
import com.sf.qzm.dao.AccountHistoryDao;
import com.sf.qzm.dao.CompanyDao;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.biz.AccountHistoryDTO;
import com.sf.qzm.dto.biz.CompanyDTO;
import com.sf.qzm.service.AccountHistoryService;

@Service
@Transactional
public class AccountHistoryServiceImpl implements AccountHistoryService {

	@Resource
	private AccountHistoryDao accountHistoryDao;
	@Resource
	private CompanyDao companyDao;
	@Override
	public void saveCompanyAccount(Integer companyId, Integer price,Integer createUserId,String comment){
		if(companyId==null){return;}
		CompanyDTO company=companyDao.get(new Company(companyId));
		if(company==null){return;}
		Company update=new Company();
		update.setCompanyId(companyId);
		update.setAccount(company.getAccount()+price);
		//更新金额。
		companyDao.update(update);
		//保存记录
		AccountHistory accountHistory=new AccountHistory();
		accountHistory.setType(1);
		accountHistory.setTag(2);//2充值
		accountHistory.setAccount(company.getAccount()+price);
		accountHistory.setCreateDate(new Date());
		accountHistory.setCreateUserId(createUserId);
		accountHistory.setPrice(price);
		accountHistory.setToId(companyId);
		accountHistory.setComment(comment);
		accountHistoryDao.save(accountHistory);
	}

	@Override
	public PageDTO<List<AccountHistoryDTO>> getHistory(PageDTO<AccountHistory> page) {
		List<AccountHistoryDTO> dateList = accountHistoryDao.list(page);
		PageDTO<List<AccountHistoryDTO>> pageDate = new PageDTO<List<AccountHistoryDTO>>();
		pageDate.setParam(dateList);
		pageDate.setPageIndex(page.getPageIndex());
		pageDate.setPageSize(page.getPageSize());
		Integer count = accountHistoryDao.count(page.getParam());
		pageDate.setCount(count+0);
		count = count % page.getPageSize() == 0 ? count / page.getPageSize()
				: count / page.getPageSize() + 1;
		pageDate.setTotalPage(count);
		return pageDate;
	}

	@Override
	public void save(AccountHistory accountHistory) {
		accountHistoryDao.save(accountHistory);
	}

}
