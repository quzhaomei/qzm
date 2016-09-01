package com.sf.qzm.bean.constant;
/**
 * 区域设置
 * @author quzhaomei
 */
public class Zone {
	private Integer zoneId;
	private String name;//区域名字
	private Integer parentId;//区域
	private Integer isDelete;
	
	
	
	/**
	 * 查询中间量
	 * @return
	 */
	private Integer tempId;
	
	public Integer getZoneId() {
		return zoneId;
	}
	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public Integer getTempId() {
		return tempId;
	}
	public void setTempId(Integer tempId) {
		this.tempId = tempId;
	}
	
}
