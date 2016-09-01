package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.menu.AutoMenu;
import com.sf.qzm.dto.menu.AutoMenuDTO;

public interface AutoMenuService extends BaseService<AutoMenu, AutoMenu>{
	/**
	 * 获取所有有效的菜单
	 * @return 菜单list
	 */
	List<AutoMenu> getAllMenu();
	/**
	 * 获取所有页面菜单
	 * @return 菜单list
	 */
	List<AutoMenu> getNavMenu();
	
	/**
	 * 查询全部导航菜单
	 * @return
	 */
	List<AutoMenuDTO> getAdminNavMenu();//查询菜单
	/**
	 * 查询用户导航菜单
	 * @param userId
	 * @return
	 */
	List<AutoMenuDTO> getAdminNavMenu(Integer userId);//查询菜单
	
	/**
	 * 查询用户所有菜单
	 * @param userId
	 * @return
	 */
	List<AutoMenuDTO> getAdminMenu(Integer userId);
	
}
