package com.sf.qzm.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.annotation.MethodCache;
import com.sf.qzm.bean.ac.Active;
import com.sf.qzm.bean.ac.ActiveCol;
import com.sf.qzm.bean.ac.ActiveVal;
import com.sf.qzm.dao.ActiveDao;
import com.sf.qzm.service.ActiveService;

@Service
@Transactional
public class ActiveServiceImpl implements ActiveService {
	
	@Resource
	private ActiveDao activeDao;

	@Override
	public List<Map<String, String>> getDataById(Integer id) {
		return activeDao.getDataById(id);
	}

	@Override
	public List<ActiveCol> getColById(Integer id) {
		return activeDao.getColById(id);
	}

	@Override
	public ActiveCol getColByIdAndPro(Integer id, String property) {
		return activeDao.getColByIdAndPro(id, property);
	}

	@Override
	public Active getById(Integer id) {
		return activeDao.getById(id);
	}

	@Override
	public  void save(List<ActiveVal> vals) {
		int valId=0;
		synchronized (activeDao) {
			valId=activeDao.getValId();
		}
		activeDao.saveVal(vals,valId);
	}


}
