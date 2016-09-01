package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.constant.Age;

public interface AgeDao {
	void save(@Param(value="age") Age age);
	void update(@Param(value="age")Age age);
	Age get(@Param(value="age")Age age);
	List<Age> list(@Param(value="age")Age age);
	int count(@Param(value="age")Age age);
}
