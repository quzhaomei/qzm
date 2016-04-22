package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.admin.MenuManager;
import com.sf.qzm.dto.admin.MenuManagerDTO;

public interface MenuManagerService extends BaseService<MenuManager, MenuManagerDTO>{
	/**
	 * 获取所有有效的菜单
	 * @return 菜单list
	 */
	List<MenuManagerDTO> getAllMenu();
	/**
	 * 获取所有页面菜单
	 * @return 菜单list
	 */
	List<MenuManagerDTO> getNavMenu();
	
}
