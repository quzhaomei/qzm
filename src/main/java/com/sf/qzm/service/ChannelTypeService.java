package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.biz.ChannelType;
import com.sf.qzm.dto.biz.ChannelTypeDTO;

public interface ChannelTypeService {
	void saveOrUpdate(ChannelType channelType);
	ChannelTypeDTO get(Integer id);
	List<ChannelTypeDTO> all();
	
	void delete(Integer typeId);
}
