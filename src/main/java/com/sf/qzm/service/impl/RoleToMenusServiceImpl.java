package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.admin.RoleManager;
import com.sf.qzm.dao.AutoMenuDao;
import com.sf.qzm.dao.RoleManagerDao;
import com.sf.qzm.dao.RoleToMenusDao;
import com.sf.qzm.dto.admin.RoleManagerDTO;
import com.sf.qzm.dto.menu.PowerMenuDTO;
import com.sf.qzm.service.RoleToMenusService;

@Service
@Transactional
public class RoleToMenusServiceImpl implements RoleToMenusService {
	@Resource
	protected RoleToMenusDao roleToMenusDao ;
	@Resource
	protected RoleManagerDao roleManagerDao;
	@Resource
	protected AutoMenuDao menuDao;
	@Override
	public List<PowerMenuDTO> getMenuTreeByRoleId(Integer roleId) {
		return roleToMenusDao .getMenuTreeByRoleId(roleId);
	}
	@Override
	public void setPowerForRole(Integer roleId, List<Integer> menuId) {
		if(roleId==null)return;
		RoleManager roleManager=new RoleManager();
		roleManager.setRoleId(roleId);
		RoleManagerDTO role=roleManagerDao.getByParam(roleManager);
		if(role!=null){
			roleToMenusDao.delBatch(roleId);
			if(menuId!=null&&menuId.size()>0){
				roleToMenusDao.saveBatch(menuId, roleId);
			}
		}
	}
	

}
