package com.sf.qzm.dto.bis;

import com.sf.qzm.dto.SfDto;

/**
 * 未接受消息统计
 * @author quzhaomei
 *
 */
public class HistoryCountDTO implements SfDto {
	private String fromId;
	private String fromName;
	private String fromHeadImg;
	private Integer checkCount;
	private Integer unCheckCount;
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	
	public String getFromHeadImg() {
		return fromHeadImg;
	}
	public void setFromHeadImg(String fromHeadImg) {
		this.fromHeadImg = fromHeadImg;
	}
	public Integer getCheckCount() {
		return checkCount;
	}
	public void setCheckCount(Integer checkCount) {
		this.checkCount = checkCount;
	}
	public Integer getUnCheckCount() {
		return unCheckCount;
	}
	public void setUnCheckCount(Integer unCheckCount) {
		this.unCheckCount = unCheckCount;
	}
	
	
}
