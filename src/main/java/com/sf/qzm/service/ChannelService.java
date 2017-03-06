package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.biz.Channel;
import com.sf.qzm.dto.biz.ChannelDTO;

public interface ChannelService {
	void saveOrUpdate(Channel channel);
	ChannelDTO get(Integer id);
	ChannelDTO get(String code);
	List<ChannelDTO> all();
	
	
	List<ChannelDTO> list(Channel channel);
	
	void delete(Integer channelId);
	
	void clear();
}
