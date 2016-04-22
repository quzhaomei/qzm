package com.sf.qzm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.admin.MenuManager;
import com.sf.qzm.bean.admin.RoleToMenus;
import com.sf.qzm.dao.MenuManagerDao;
import com.sf.qzm.dao.RoleToMenusDao;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.admin.MenuManagerDTO;
import com.sf.qzm.dto.admin.RoleToMenusDTO;
import com.sf.qzm.service.RoleToMenusService;

@Service
@Transactional
public class RoleToMenusServiceImpl implements RoleToMenusService {
	@Resource
	protected RoleToMenusDao roleToMenusDao;
	@Resource
	protected MenuManagerDao menuDao;
	@Override
	public void saveOrUpdate(RoleToMenus bean) throws Exception {
		if(bean.getRoleId()!=null){
			roleToMenusDao.update(bean);
		}else{
			roleToMenusDao.save(bean);
		}
	}

	@Override
	public void delete(RoleToMenus bean) {
		
	}

	@Override
	public RoleToMenusDTO getById(Integer id) {
		return null;
	}

	@Override
	public RoleToMenusDTO getByParam(RoleToMenus bean) {
		return null;
	}

	@Override
	public List<RoleToMenusDTO> getListByParam(RoleToMenus bean) {
		return null;
	}

	@Override
	public PageDTO<List<RoleToMenusDTO>> getPageByParam(PageDTO<RoleToMenus> bean) {
		return null;
	}

	@Override
	public List<MenuManagerDTO> getRoleMenusByRoleId(Integer id) {
		RoleToMenus roleToMenus=new RoleToMenus();
		roleToMenus.setRoleId(id);
		return roleToMenusDao.getMenuListByParam(roleToMenus);
	}

	@Override
	public List<MenuManagerDTO> getUseFullMenusByRoleId(Integer id) {
		RoleToMenus roleToMenus=new RoleToMenus();
		roleToMenus.setRoleId(id);
		return roleToMenusDao.getMenuListByRole(roleToMenus);
	}

	@Override
	public void batchUpdateRoleMenus(String[] menuIds, String[] tempIds, Integer roleId) throws Exception {
		boolean dateCheck=true;
		List<Integer>menuIdList=null;
		List<Integer>tempIdList=null;
		//数据检测
			if(menuIds!=null){
				menuIdList=new ArrayList<Integer>();
				for(String menuId:menuIds){
					if(menuId.matches("\\d+")){
						Integer menuIdInt=Integer.parseInt(menuId);
						menuIdList.add(menuIdInt);
						
						MenuManager manager=new MenuManager();
						manager.setMenuId(menuIdInt);
						
						MenuManagerDTO tempMenu=menuDao.getByParam(manager);
						if(tempMenu==null){//验证是否存在
							dateCheck=false;
							break;
						}
						//验证是否已经保存过
						RoleToMenus roleToMenus=new RoleToMenus();
						roleToMenus.setMenuId(menuIdInt);
						roleToMenus.setRoleId(roleId);
						RoleToMenusDTO roleToMenusDTO=roleToMenusDao.getByParam(roleToMenus);
						if(roleToMenusDTO!=null){//如果保存过，则数据错误
							dateCheck=false;
							break;
						}
					}
				}
			}
			if(tempIds!=null){
				tempIdList=new ArrayList<Integer>();
				for(String tempId:tempIds){
					if(tempId.matches("\\d+")){
						Integer tempIdInt=Integer.parseInt(tempId);
						tempIdList.add(tempIdInt);
						
						RoleToMenus roleToMenus=new RoleToMenus();
						roleToMenus.setId(tempIdInt);
						roleToMenus.setRoleId(roleId);
						RoleToMenusDTO roleToMenusDTO=roleToMenusDao.getByParam(roleToMenus);
						if(roleToMenusDTO==null){
							dateCheck=false;
							break;
						}
					}
				}
			}
		if(!dateCheck){//数据不合理
			throw new Exception("数据错误,请刷新再试");
		}
		//开始更新,增加菜单
		if(menuIdList!=null){
			roleToMenusDao.saveBatch(menuIdList, roleId);
		}
		//开始更新，删除菜单
		if(tempIdList!=null){
			roleToMenusDao.delBatch(tempIdList, roleId);
		}
	}

}
