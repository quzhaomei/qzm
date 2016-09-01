package com.sf.qzm.bean.constant;

/**
 * 年龄段管理
 * @author quzhaomei
 */
public class Age {
	private Integer ageId;
	private Integer start;
	private Integer end;
	private String name;
	private Integer isDelete;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAgeId() {
		return ageId;
	}
	public void setAgeId(Integer ageId) {
		this.ageId = ageId;
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
