package com.sf.qzm.bean.biz;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CustomerHouse {
	/**
	 * 房子id
	 */
	private Integer houseId;
	
	/**
	 * 客户id
	 */
	private Integer customerId;
	/**
	 * 区域
	 */
	private Integer zoneId;
	/**
	 * 楼盘信息
	 */
	private String houseInfo;
	/**
	 * 房屋地址
	 */
	private String houseLocation;
	/**
	 * 房屋类型
	 */
	private Integer houseTypeId;
	/**
	 * 房屋类别 0-旧房，1-新房
	 */
	private Integer isNew;
	/**
	 * 房屋面积
	 */
	private Integer area;
	/**
	 * 装修类别 0-半包，1-全包
	 */
	private Integer designType;
	/**
	 * 装修预算
	 */
	private Integer budgetId;
	
	/**
	 * 客户备注
	 */
	private String comment;
	
	
	/**
	 * 回访备注
	 */
	private String callbackTips;
	
	/**
	 * 操作日志
	 */
	private String log;
	
	private Date createDate;
	private Integer createUserId;
	/**
	 * 0-未发布，1-已发布,2-已派单,3-关闭
	 */
	private Integer status;
	/**
	 * 是否有软装需求 0-没有，1-有
	 */
	private Integer hasSoft;
	
	/**
	 * 0-未派单，1-已派单,2-关闭
	 */
	private Integer softStatus;//软装状态
	
	/**
	 * 是否配送了礼包，0-否，1-是
	 */
	private Integer gift;
	/**
	 * 礼包地址
	 */
	private String giftAddress;
	
	private Integer isDelete;
	
	/**
	 * 辅助字段开始	 */
	private Integer zoneRootId;
	private Integer zoneParentId;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createDate_start;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createDate_end;
	
	
	
	public Integer getZoneRootId() {
		return zoneRootId;
	}
	public void setZoneRootId(Integer zoneRootId) {
		this.zoneRootId = zoneRootId;
	}
	public Integer getZoneParentId() {
		return zoneParentId;
	}
	public void setZoneParentId(Integer zoneParentId) {
		this.zoneParentId = zoneParentId;
	}
	public Integer getHouseId() {
		return houseId;
	}
	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}
	public Integer getZoneId() {
		return zoneId;
	}
	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}
	public String getHouseInfo() {
		return houseInfo;
	}
	public void setHouseInfo(String houseInfo) {
		this.houseInfo = houseInfo;
	}
	public String getHouseLocation() {
		return houseLocation;
	}
	public void setHouseLocation(String houseLocation) {
		this.houseLocation = houseLocation;
	}
	public Integer getHouseTypeId() {
		return houseTypeId;
	}
	public void setHouseTypeId(Integer houseTypeId) {
		this.houseTypeId = houseTypeId;
	}
	public Integer getIsNew() {
		return isNew;
	}
	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public Integer getDesignType() {
		return designType;
	}
	public void setDesignType(Integer designType) {
		this.designType = designType;
	}
	public Integer getBudgetId() {
		return budgetId;
	}
	public void setBudgetId(Integer budgetId) {
		this.budgetId = budgetId;
	}
	public Integer getHasSoft() {
		return hasSoft;
	}
	public void setHasSoft(Integer hasSoft) {
		this.hasSoft = hasSoft;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public Integer getGift() {
		return gift;
	}
	public void setGift(Integer gift) {
		this.gift = gift;
	}
	public String getGiftAddress() {
		return giftAddress;
	}
	public void setGiftAddress(String giftAddress) {
		this.giftAddress = giftAddress;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getCallbackTips() {
		return callbackTips;
	}
	public void setCallbackTips(String callbackTips) {
		this.callbackTips = callbackTips;
	}
	public Date getCreateDate_start() {
		return createDate_start;
	}
	public void setCreateDate_start(Date createDate_start) {
		this.createDate_start = createDate_start;
	}
	public Date getCreateDate_end() {
		return createDate_end;
	}
	public void setCreateDate_end(Date createDate_end) {
		this.createDate_end = createDate_end;
	}
	
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public Integer getSoftStatus() {
		return softStatus;
	}
	public void setSoftStatus(Integer softStatus) {
		this.softStatus = softStatus;
	}
	public String noReadyMsg(){
		if(this.getZoneId()==null){
			return "＊区域未填写 或 未提交";
		}else if(this.getHouseInfo()==null){
			return "＊楼盘信息未填写 或 未提交";
		}else if(this.getIsNew()==null){
			return "＊房屋类别未填写 或 未提交";
		}else if(this.getArea()==null){
			return "＊装修面积未填写 或 未提交";
		}else if(this.getComment()==null){
			return "＊需求备注未填写 或 未提交";
		}
		return null;
	}
}
