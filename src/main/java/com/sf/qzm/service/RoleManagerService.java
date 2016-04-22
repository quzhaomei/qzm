package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.admin.RoleManager;
import com.sf.qzm.dto.admin.RoleManagerDTO;

public interface RoleManagerService extends BaseService<RoleManager, RoleManagerDTO>{
	/**
	 * 获取所有角色
	 * @return 菜单list
	 */
	List<RoleManagerDTO> getAllRole();

}
