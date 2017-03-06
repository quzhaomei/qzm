package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.biz.Channel;
import com.sf.qzm.dao.ChannelDao;
import com.sf.qzm.dto.biz.ChannelDTO;
import com.sf.qzm.service.ChannelService;

@Service
@Transactional
public class ChannelServiceImpl implements ChannelService{

	@Resource
	private ChannelDao channelDao;
	@Override
	public void saveOrUpdate(Channel channel) {
		if(channel.getChannelId()==null){
			channelDao.save(channel);
		}else{
			channelDao.update(channel);
		}
	}

	@Override
	public ChannelDTO get(Integer id) {
		if(id==null)return null;
		Channel channel=new Channel();
		channel.setChannelId(id);
		return channelDao.get(channel);
	}

	@Override
	public ChannelDTO get(String code) {
		if(code==null)return null;
		Channel channel=new Channel();
		channel.setCode(code);
		return channelDao.get(channel);
	}

	@Override
	public List<ChannelDTO> all() {
		return channelDao.list(new Channel());
	}

	@Override
	public void delete(Integer channelId) {
		if(channelId==null) return;
		Channel channel=new Channel();
		channel.setChannelId(channelId);
		channel.setIsDelete(1);
		channelDao.update(channel);
	}

	@Override
	public void clear() {
		channelDao.clear();
	}

	@Override
	public List<ChannelDTO> list(Channel channel) {
		return channelDao.list(channel);
	}	

}
