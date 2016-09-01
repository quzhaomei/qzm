package com.sf.qzm.dto.ac;

import com.sf.qzm.dto.SfDto;

public class ActiveColDTO implements SfDto {
	private Integer id;
	private String name;
	private Integer activeId;
	private String property;
	private Integer type;
	private Integer require;
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
