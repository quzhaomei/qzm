package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.bis.SystemSource;
import com.sf.qzm.dao.SystemSourceDao;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.bis.SystemSourceDTO;
import com.sf.qzm.service.SystemSourceService;

@Service
@Transactional
public class SystemSourceServiceImpl implements SystemSourceService{
	@Resource
	private SystemSourceDao systemSourceDao;
	@Override
	public void saveOrUpdate(SystemSource bean) throws Exception {
		if(bean.getSourceId()!=null){
			systemSourceDao.update(bean);
		}else{
			systemSourceDao.save(bean);
		}
	}

	@Override
	public void delete(SystemSource bean) {
		systemSourceDao.delete(bean);
	}

	@Override
	public SystemSourceDTO getById(Integer id) {
		SystemSource param=new SystemSource();
		param.setSourceId(id);
		return systemSourceDao.getByParam(param);
	}

	@Override
	public SystemSourceDTO getByParam(SystemSource bean) {
		return systemSourceDao.getByParam(bean);
	}

	@Override
	public List<SystemSourceDTO> getListByParam(SystemSource bean) {
		return systemSourceDao.getListByParam(bean);
	}

	@Override
	public PageDTO<List<SystemSourceDTO>> getPageByParam(PageDTO<SystemSource> bean) {
		return null;
	}

	@Override
	public String getByKey(String key) {
		if(key==null){
			return null;
		}
		SystemSource param=new SystemSource();
		param.setKey(key);
		SystemSourceDTO source=systemSourceDao.getByParam(param);
		if(source!=null){
			return source.getValue();
		}else{
			return null;
		}
	}

}
