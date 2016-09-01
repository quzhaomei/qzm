package com.sf.qzm.bean.constant;

/**
 * 预算
 * @author quzhaomei
 */
public class Budget {
	private Integer budgetId;
	private String name;
	private Integer start;
	private Integer end;
	private Integer isDelete;
	
	public Integer getBudgetId() {
		return budgetId;
	}
	public void setBudgetId(Integer budgetId) {
		this.budgetId = budgetId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getEnd() {
		return end;
	}
	public void setEnd(Integer end) {
		this.end = end;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
}
