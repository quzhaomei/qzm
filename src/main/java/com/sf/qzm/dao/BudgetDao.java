package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.constant.Budget;

public interface BudgetDao {
	void save(@Param(value="budget") Budget budget);
	void update(@Param(value="budget")Budget budget);
	Budget get(@Param(value="budget")Budget budget);
	List<Budget> list(@Param(value="budget")Budget budget);
	int count(@Param(value="budget")Budget budget);
}
