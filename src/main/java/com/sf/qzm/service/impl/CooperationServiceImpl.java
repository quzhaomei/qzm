package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.bis.Cooperation;
import com.sf.qzm.dao.CooperationDao;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.bis.CooperationDTO;
import com.sf.qzm.service.CooperationService;
@Service
@Transactional
public class CooperationServiceImpl implements CooperationService {
	@Resource
	private CooperationDao cooperationDao;

	@Override
	public void saveOrUpdate(Cooperation bean) throws Exception {
		if(bean.getCooperationId()!=null){
			cooperationDao.update(bean);
		}else{
			cooperationDao.save(bean);
		}
	}

	@Override
	public void delete(Cooperation bean) {
		
	}

	@Override
	public CooperationDTO getById(Integer id) {
		Cooperation bean=new Cooperation();
		if(id!=null){
			bean.setCooperationId(id);
			return cooperationDao.getByParam(bean);
		}
		return null;
	}

	@Override
	public CooperationDTO getByParam(Cooperation bean) {
		return cooperationDao.getByParam(bean);
	}

	@Override
	public List<CooperationDTO> getListByParam(Cooperation bean) {
		return cooperationDao.getListByParam(bean);
	}

	@Override
	public PageDTO<List<CooperationDTO>> getPageByParam(PageDTO<Cooperation> bean) {
		return null;
	}

	@Override
	public CooperationDTO getByCode(String code) {
		Cooperation bean=new Cooperation();
		if(code!=null){
			bean.setCode(code);
			return cooperationDao.getByParam(bean);
		}
		return null;
	}

}
