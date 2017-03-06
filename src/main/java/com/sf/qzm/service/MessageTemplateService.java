package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.biz.MessageTemplate;
import com.sf.qzm.dto.biz.MessageTemplateDTO;

public interface MessageTemplateService {
	List<MessageTemplateDTO> all();
	
	List<MessageTemplateDTO> allLive();
	void saveOrUpdate(MessageTemplate messageTemplate);
	MessageTemplateDTO get(Integer templateId);
	void delete(Integer templateId);
}
