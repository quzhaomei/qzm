package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.biz.MessageTemplate;
import com.sf.qzm.dao.MessageTemplateDao;
import com.sf.qzm.dto.biz.MessageTemplateDTO;
import com.sf.qzm.service.MessageTemplateService;

@Service
@Transactional
public class MessageTemplateServiceImpl implements MessageTemplateService {

	@Resource
	private MessageTemplateDao messageTemplateDao;
	@Override
	public List<MessageTemplateDTO> all() {
		return messageTemplateDao.list(new MessageTemplate());
	}
	@Override
	public void saveOrUpdate(MessageTemplate messageTemplate) {
		if(messageTemplate.getTemplateId()!=null){
			messageTemplateDao.update(messageTemplate);
		}else{
			messageTemplateDao.save(messageTemplate);
		}
	}
	@Override
	public void delete(Integer templateId) {
		if(templateId==null){return;}
		MessageTemplate param=new MessageTemplate();
		param.setIsDelete(1);
		param.setTemplateId(templateId);
		messageTemplateDao.update(param);
	}
	@Override
	public MessageTemplateDTO get(Integer templateId) {
		if(templateId==null){return null;}
		MessageTemplate param=new MessageTemplate();
		param.setTemplateId(templateId);
		return messageTemplateDao.get(param);
	}
	@Override
	public List<MessageTemplateDTO> allLive() {
		MessageTemplate param=new MessageTemplate();
		param.setStatus(1);
		return messageTemplateDao.list(param);
	}

}
