package com.sf.qzm.service;

import com.sf.qzm.bean.admin.AdminUser;
import com.sf.qzm.dto.admin.AdminUserDTO;

public interface AdminUserService extends BaseService<AdminUser, AdminUserDTO> {
	int getCountByParam(AdminUser adminUser);
	
}
