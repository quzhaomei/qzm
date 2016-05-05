package com.sf.qzm.service;

import com.sf.qzm.bean.bis.Cooperation;
import com.sf.qzm.dto.bis.CooperationDTO;

public interface CooperationService extends BaseService<Cooperation, CooperationDTO>{
	CooperationDTO getByCode(String key);
}
