package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.menu.AutoMenu;
import com.sf.qzm.dto.menu.AutoMenuDTO;

public interface AutoMenuDao {
	void save(@Param(value="menu") AutoMenu menu);//增
	void update(@Param(value="menu")AutoMenu menu);//改
	void delete(@Param(value="menu")AutoMenu menu);//删
	List<AutoMenu> getListByParam(@Param(value="menu")AutoMenu menu);//查询数组
	AutoMenu getByParam(@Param(value="menu")AutoMenu menut);//查询单个
	List<AutoMenu> getNavMenu();//查询导航菜单
	
	//
	List<AutoMenuDTO> getAdminNavMenu();//查询菜单
	List<AutoMenuDTO> getAdminNavMenuByUserId(@Param(value="userId")Integer userId);
	
	List<AutoMenuDTO> getAdminMenu(@Param(value="userId")Integer userId);
	
	void clear();
}
