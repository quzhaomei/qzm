package com.sf.qzm.dto.biz;

import com.sf.qzm.bean.constant.Zone;

public class CompanyZoneDTO {
	private Integer id;
	private CompanyDTO company;
	private Zone zone;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public CompanyDTO getCompany() {
		return company;
	}
	public void setCompany(CompanyDTO company) {
		this.company = company;
	}
	public Zone getZone() {
		return zone;
	}
	public void setZone(Zone zone) {
		this.zone = zone;
	}
	
}
