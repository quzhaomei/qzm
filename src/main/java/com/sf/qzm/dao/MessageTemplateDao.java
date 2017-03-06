package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.biz.MessageTemplate;
import com.sf.qzm.dto.biz.MessageTemplateDTO;

public interface MessageTemplateDao {
	void save(@Param(value="template") MessageTemplate template);
	void update(@Param(value="template")MessageTemplate template);
	MessageTemplateDTO get(@Param(value="template")MessageTemplate template);
	List<MessageTemplateDTO> list(@Param(value="template")MessageTemplate template);
}
