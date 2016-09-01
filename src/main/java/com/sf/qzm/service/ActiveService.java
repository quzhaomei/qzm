package com.sf.qzm.service;

import java.util.List;
import java.util.Map;

import com.sf.qzm.bean.ac.Active;
import com.sf.qzm.bean.ac.ActiveCol;
import com.sf.qzm.bean.ac.ActiveVal;

public interface ActiveService {
	void save(List<ActiveVal> vals);
	Active getById( Integer id);
	List<Map<String,String>> getDataById(Integer id);
	List<ActiveCol> getColById(Integer id);
	ActiveCol getColByIdAndPro( Integer id,String property);
}
