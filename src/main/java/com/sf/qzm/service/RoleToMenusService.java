package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.dto.menu.PowerMenuDTO;

public interface RoleToMenusService {
	List<PowerMenuDTO> getMenuTreeByRoleId(Integer roleId);//查询数组
	void setPowerForRole(Integer roleId,List<Integer> menuId);
}
