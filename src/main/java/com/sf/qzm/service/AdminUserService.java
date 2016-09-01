package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.admin.AdminUser;
import com.sf.qzm.dto.admin.AdminUserDTO;

public interface AdminUserService extends BaseService<AdminUser, AdminUserDTO> {
	int getCountByParam(AdminUser adminUser);
	List<AdminUserDTO> getAllUser();
	AdminUserDTO getUser(String username,String password);
	AdminUserDTO getUser(String username);
	void delete(Integer userId);
	
	List<AdminUserDTO> getByPower(String powerCode);
	
	
	AdminUserDTO checkPower(String powerCode,Integer userId);
}
