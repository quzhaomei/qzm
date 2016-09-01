package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.constant.Budget;

public interface BudgetService {
	void saveOrUpdate(Budget budget);
	Budget getById(Integer budgetId);
	List<Budget> all();
	void delete(Integer budgetId);
	
}
