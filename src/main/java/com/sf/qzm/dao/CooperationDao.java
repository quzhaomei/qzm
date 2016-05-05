package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.bis.Cooperation;
import com.sf.qzm.dto.bis.CooperationDTO;

public interface CooperationDao {
	void save(@Param(value="cooperation") Cooperation bean);//增
	void update(@Param(value="cooperation")Cooperation bean);//改
	void delete(Cooperation role);//删
	List<CooperationDTO> getListByParam(@Param(value="cooperation")Cooperation bean);//查询数组
	CooperationDTO getByParam(@Param(value="cooperation")Cooperation bean);//查询单个
	int getCountByParam(Cooperation bean);//查询数量

}
