package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.bis.SystemSource;
import com.sf.qzm.dto.bis.SystemSourceDTO;

public interface SystemSourceDao {
	void save(@Param(value="source") SystemSource role);//增
	void update(@Param(value="source")SystemSource role);//改
	void delete(SystemSource role);//删
	List<SystemSourceDTO> getListByParam(@Param(value="source")SystemSource role);//查询数组
	SystemSourceDTO getByParam(@Param(value="source")SystemSource role);//查询单个
	int getCountByParam(SystemSource role);//查询数量

	Object executeSql(@Param(value="sql") String sql);
}
