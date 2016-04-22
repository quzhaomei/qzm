package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.admin.MenuManager;
import com.sf.qzm.dto.admin.MenuManagerDTO;

public interface MenuManagerDao{
	void save(@Param(value="menu") MenuManager menu);//增
	void update(@Param(value="menu")MenuManager menu);//改
	void delete(@Param(value="menu")MenuManager menu);//删
	List<MenuManagerDTO> getListByParam(@Param(value="menu")MenuManager menu);//查询数组
	MenuManagerDTO getByParam(@Param(value="menu")MenuManager menut);//查询单个
	List<MenuManagerDTO> getNavMenu();//查询导航菜单
}
