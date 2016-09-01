package com.sf.qzm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.ac.Active;
import com.sf.qzm.bean.ac.ActiveCol;
import com.sf.qzm.bean.ac.ActiveVal;

public interface ActiveDao {
	Active getById(@Param(value="id") Integer id);
	int getValId();
	void saveVal(@Param(value="vals") List<ActiveVal> vals,@Param(value="valId") Integer valId);
	void truncateActive(@Param(value="activeId") Integer activeId);
	
	List<Map<String,String>> getDataById(@Param(value="id") Integer id);
	List<ActiveCol> getColById(@Param(value="id") Integer id);
	ActiveCol getColByIdAndPro(@Param(value="id") Integer id,@Param(value="property") String property);
}
