package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.biz.ChannelType;
import com.sf.qzm.dto.biz.ChannelTypeDTO;

public interface ChannelTypeDao {
	void save(@Param(value="type") ChannelType channelType);
	void update(@Param(value="type")ChannelType channelType);
	ChannelTypeDTO get(@Param(value="type")ChannelType channelType);
	List<ChannelTypeDTO> list(@Param(value="type")ChannelType channelType);
	int count(@Param(value="type")ChannelType channelType);
}
