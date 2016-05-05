package com.sf.qzm.service;

import com.sf.qzm.bean.bis.SystemSource;
import com.sf.qzm.dto.bis.SystemSourceDTO;

public interface SystemSourceService extends BaseService<SystemSource, SystemSourceDTO>{
	String getByKey(String key);
}
