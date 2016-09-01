package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.constant.Budget;
import com.sf.qzm.dao.BudgetDao;
import com.sf.qzm.service.BudgetService;

@Service
@Transactional
public class BudgetServiceImpl implements BudgetService {
	@Resource
	private BudgetDao budgetDao;
	@Override
	public void saveOrUpdate(Budget budget) {
		if(budget.getBudgetId()==null){
			budgetDao.save(budget);
		}else{
			budgetDao.update(budget);
		}
	}

	@Override
	public Budget getById(Integer budgetId) {
		if(budgetId==null){return null;}
		Budget param=new Budget();
		param.setBudgetId(budgetId);
		return budgetDao.get(param);
	}

	@Override
	public List<Budget> all() {
		return budgetDao.list(new Budget());
	}

	@Override
	public void delete(Integer budgetId) {
		if(budgetId==null){return;}
		Budget param=new Budget();
		param.setBudgetId(budgetId);
		param.setIsDelete(1);
		budgetDao.update(param);
	}

}
