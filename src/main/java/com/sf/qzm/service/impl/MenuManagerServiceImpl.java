package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.admin.MenuManager;
import com.sf.qzm.dao.MenuManagerDao;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.admin.MenuManagerDTO;
import com.sf.qzm.service.MenuManagerService;
@Service
@Transactional
public class MenuManagerServiceImpl implements MenuManagerService{
	@Resource
	protected MenuManagerDao menuManagerDao;
	@Override
	public void saveOrUpdate(MenuManager bean) throws Exception {
		if(bean.getMenuId()==null){
			menuManagerDao.save(bean);
		}else {
			menuManagerDao.update(bean);
		}
	}

	@Override
	public void delete(MenuManager bean) {
		menuManagerDao.delete(bean);
	}

	@Override
	public MenuManagerDTO getById(Integer id) {
		if(null !=id){
			MenuManager bean =new MenuManager();
			bean.setMenuId(id);
			return menuManagerDao.getByParam(bean);
		}
		return null;
	}

	@Override
	public MenuManagerDTO getByParam(MenuManager bean) {
		return menuManagerDao.getByParam(bean);
	}

	@Override
	public List<MenuManagerDTO> getListByParam(MenuManager bean) {
		return menuManagerDao.getListByParam(bean);
	}

	@Override
	public PageDTO<List<MenuManagerDTO>> getPageByParam(PageDTO<MenuManager> bean) {
		return null;
	}

	@Override
	public List<MenuManagerDTO> getAllMenu() {
		MenuManager menu=new MenuManager();
		menu.setStatus(1);
		return menuManagerDao.getListByParam(menu);
	}

	@Override
	public List<MenuManagerDTO> getNavMenu() {
		return menuManagerDao.getNavMenu();
	}

}
