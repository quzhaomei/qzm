package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.admin.RoleManager;
import com.sf.qzm.dao.RoleManagerDao;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.admin.RoleManagerDTO;
import com.sf.qzm.service.RoleManagerService;

@Service
@Transactional
public class RoleManagerServiceImpl implements RoleManagerService{
	@Resource
	protected RoleManagerDao roleManagerDao;
	@Override
	public void saveOrUpdate(RoleManager bean) throws Exception {
		Integer id=bean.getRoleId();
		if(id==null){
			roleManagerDao.save(bean);
		}else{
			roleManagerDao.update(bean);
		}
		
	}

	@Override
	public void delete(RoleManager bean) {
		
	}

	@Override
	public RoleManagerDTO getById(Integer id) {
		if(id==null){
			return null;
		}
		RoleManager manager=new RoleManager();
		manager.setRoleId(id);
		return roleManagerDao.getByParam(manager);
	}

	@Override
	public RoleManagerDTO getByParam(RoleManager bean) {
		return roleManagerDao.getByParam(bean);
	}

	@Override
	public List<RoleManagerDTO> getListByParam(RoleManager bean) {
		return roleManagerDao.getListByParam(bean);
	}

	@Override
	public PageDTO<List<RoleManagerDTO>> getPageByParam(PageDTO<RoleManager> bean) {
		return null;
	}

	@Override
	public List<RoleManagerDTO> getAllRole() {
		return roleManagerDao.getListByParam(new RoleManager());
	}

	@Override
	public void setRoleNameById(Integer roleId, String name) {
		RoleManager param=new RoleManager();
		param.setRoleId(roleId);
		param.setRoleName(name);
		roleManagerDao.update(param);
	}

}
