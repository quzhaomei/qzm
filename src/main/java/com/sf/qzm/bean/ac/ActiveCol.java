package com.sf.qzm.bean.ac;

public class ActiveCol {
	private Integer id;
	private String name;
	private Integer activeId;
	private String property;
	/**
	 * 0－ 普通文本输入框
	 * 1－ 电话号码
	 * 2－ 邮箱
	 * 3－ 时间
	 * 4－ 单选框
	 * 5－ 多选框
	 * 6－单下拉选框
	 * 7－级联选框
	 */
	private Integer type;
	/**
	 * 是否需要
	 * 0- 不一定
	 * 1- 需要
	 */
	private Integer require;
	/**
	 * 针对控件的，多个值用","隔开
	 * 4-单选   例如  男,女
	 * 5-多选   例如  周一，周二，周三
	 * 6-单下拉框  放链接servleturl   demo.html
	 * 7-级联下拉框 放 demo1.html,demo2.html,demo3.html
	 */
	private String resource;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getActiveId() {
		return activeId;
	}
	public void setActiveId(Integer activeId) {
		this.activeId = activeId;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getRequire() {
		return require;
	}
	public void setRequire(Integer require) {
		this.require = require;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	
}
