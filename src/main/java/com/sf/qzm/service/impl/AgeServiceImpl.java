package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.constant.Age;
import com.sf.qzm.dao.AgeDao;
import com.sf.qzm.service.AgeService;

@Transactional
@Service
public class AgeServiceImpl implements AgeService {
	
	@Resource
	private AgeDao ageDao;
	
	@Override
	public void saveOrUpdate(Age age) {
		if(age.getAgeId()==null){
			ageDao.save(age);
		}else{
			ageDao.update(age);
		}
	}

	@Override
	public List<Age> all() {
		
		return ageDao.list(new Age());
	}


	@Override
	public Age getById(Integer ageId) {
		if(ageId==null){return null;}
		Age param=new Age();
		param.setAgeId(ageId);
		return param;
	}

	@Override
	public void delete(Integer ageId) {
		if(ageId==null){return;}
		Age param=new Age();
		param.setAgeId(ageId);
		param.setIsDelete(1);
		 ageDao.update(param);
	}

}
