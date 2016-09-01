package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.admin.RoleToMenus;
import com.sf.qzm.dto.menu.PowerMenuDTO;

public interface RoleToMenusDao {
	void save(@Param(value="roleToMenus") RoleToMenus roleToMenus);//增
	void update(@Param(value="roleToMenus")RoleToMenus roleToMenus);//改
	List<PowerMenuDTO> getMenuTreeByRoleId(@Param(value="roleId") Integer roleId);//查询数组
	
	int getCountByParam(RoleToMenus roleToMenus);//查询数量
	void delBatch(@Param(value="roleId")Integer roleId);//批量删除
	void saveBatch(@Param(value="menuIds")List<Integer> menuIds,@Param(value="roleId")Integer roleId);//批量增加
}
