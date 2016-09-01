package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.biz.Channel;
import com.sf.qzm.dto.biz.ChannelDTO;

public interface ChannelDao {
	void save(@Param(value="channel") Channel channel);
	void update(@Param(value="channel")Channel channel);
	ChannelDTO get(@Param(value="channel")Channel channel);
	List<ChannelDTO> list(@Param(value="channel")Channel channel);
	int count(@Param(value="channel")Channel channel);
	
	void clear();
}
