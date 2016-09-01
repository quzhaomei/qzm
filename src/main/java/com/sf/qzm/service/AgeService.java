package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.constant.Age;

public interface AgeService {
	void saveOrUpdate(Age age);
	Age getById(Integer ageId);
	List<Age> all();
	void delete(Integer ageId);
	
}
