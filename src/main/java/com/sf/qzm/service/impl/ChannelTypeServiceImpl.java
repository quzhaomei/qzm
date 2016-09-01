package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.biz.ChannelType;
import com.sf.qzm.dao.ChannelTypeDao;
import com.sf.qzm.dto.biz.ChannelTypeDTO;
import com.sf.qzm.service.ChannelTypeService;

@Service
@Transactional
public class ChannelTypeServiceImpl implements ChannelTypeService {

	@Resource
	private ChannelTypeDao channelTypeDao;
	@Override
	public void saveOrUpdate(ChannelType channelType) {
		if(channelType.getTypeId()!=null){
			channelTypeDao.update(channelType);
		}else{
			channelTypeDao.save(channelType);
		}
	}

	@Override
	public ChannelTypeDTO get(Integer id) {
		if(id!=null){
			ChannelType param=new ChannelType();
			param.setTypeId(id);
			return channelTypeDao.get(param);
		}
		return null;
	}

	@Override
	public List<ChannelTypeDTO> all() {
		return channelTypeDao.list(new ChannelType());
	}

	@Override
	public void delete(Integer typeId) {
		if(typeId!=null){
			ChannelType param=new ChannelType();
			param.setTypeId(typeId);
			param.setIsDelete(1);
			channelTypeDao.update(param);
		}
	}

}
