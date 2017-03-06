package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.menu.AutoMenu;
import com.sf.qzm.dao.AutoMenuDao;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.menu.AutoMenuDTO;
import com.sf.qzm.service.AutoMenuService;

@Service
@Transactional
public class AutoMenuServiceImpl implements AutoMenuService {
	@Resource
	private AutoMenuDao autoMenuDao;
	@Override
	public void saveOrUpdate(AutoMenu bean) throws Exception {
		if(bean.getMenuId()!=null){
			autoMenuDao.update(bean);
		}else{
			autoMenuDao.save(bean);
		}
	}

	@Override
	public void delete(AutoMenu bean) {
		autoMenuDao.delete(bean);
	}

	@Override
	public AutoMenu getById(Integer id) {
		if(id!=null){
			AutoMenu menu=new AutoMenu();
			menu.setMenuId(id);
			return autoMenuDao.getByParam(menu);
			
		}else{
			return null;
		}
	}

	@Override
	public AutoMenu getByParam(AutoMenu bean) {
		return autoMenuDao.getByParam(bean);
	}

	@Override
	public List<AutoMenu> getListByParam(AutoMenu bean) {
		return autoMenuDao.getListByParam(bean);
	}

	@Override
	public PageDTO<List<AutoMenu>> getPageByParam(PageDTO<AutoMenu> bean) {
		return null;
	}

	@Override
	public List<AutoMenu> getAllMenu() {
		return autoMenuDao.getListByParam(new AutoMenu());
	}

	@Override
	public List<AutoMenu> getNavMenu() {
		return autoMenuDao.getNavMenu();
	}

	@Override
	public List<AutoMenuDTO> getAdminNavMenu() {
		return autoMenuDao.getAdminNavMenu();
	}

	@Override
	public List<AutoMenuDTO> getAdminNavMenu(Integer userId) {
		return autoMenuDao.getAdminNavMenuByUserId(userId);
	}

	@Override
	public List<AutoMenuDTO> getAdminMenu(Integer userId) {
		return autoMenuDao.getAdminMenu(userId);
	}

	@Override
	public AutoMenu getByCode(String code) {
		if(code==null){return null;}
		AutoMenu param=new AutoMenu();
		param.setCode(code);
		return autoMenuDao.getByParam(param);
	}

	@Override
	public void clear() {
		autoMenuDao.clear();
	}


}
