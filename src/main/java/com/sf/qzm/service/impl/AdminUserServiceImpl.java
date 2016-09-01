package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.admin.AdminUser;
import com.sf.qzm.bean.admin.RoleManager;
import com.sf.qzm.dao.AdminUserDao;
import com.sf.qzm.dao.RoleManagerDao;
import com.sf.qzm.dao.UserToRoleDao;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.admin.AdminUserDTO;
import com.sf.qzm.dto.admin.RoleManagerDTO;
import com.sf.qzm.service.AdminUserService;
import com.sf.qzm.util.page.PageUtils;

@Service(value="adminUserService")
@Transactional
public class AdminUserServiceImpl implements AdminUserService {
	@Resource
	protected AdminUserDao adminUserDao;
	@Resource
	protected UserToRoleDao userToRoleDao;
	@Resource
	protected RoleManagerDao roleManagerDao;
	@Override
	public void saveOrUpdate(AdminUser bean) throws Exception {
		if (null == bean.getAdminUserId()) {
			saveUserAndRole(bean, bean.getRoleIds());
		} else {
			updateUserAndRole(bean, bean.getRoleIds());
		}
	}

	@Override
	public void delete(AdminUser bean) {
		
	}

	@Override
	public AdminUserDTO getById(Integer id) {
		if(id!=null){
			AdminUser bean=new AdminUser();
			bean.setAdminUserId(id);
			return adminUserDao.getByParam(bean);
		}
		return null;
	}

	@Override
	public AdminUserDTO getByParam(AdminUser bean) {
		return adminUserDao.getByParam(bean);
	}

	@Override
	public List<AdminUserDTO> getListByParam(AdminUser bean) {
		return adminUserDao.getListByParam(bean);
	}

	@Override
	public PageDTO<List<AdminUserDTO>> getPageByParam(PageDTO<AdminUser> bean) {
		List<AdminUserDTO> dateList = adminUserDao.getListByPage(bean);
		int count = adminUserDao.getCountByParam(bean.getParam());
		return PageUtils.parseFrom(dateList, count, bean);
	}
	/**
	 * 根据用户 以及角色id 保存用户
	 * 
	 * @param user
	 *            用户
	 * @param roleIds
	 *            角色id
	 * @throws Exception
	 */
	private void saveUserAndRole(AdminUser user, List<Integer> roleIds) throws Exception {
		// 验证数据，
		if (roleIds != null) {
			for (Integer roleId : roleIds) {
				RoleManager role = new RoleManager();
				role.setRoleId(roleId);
				RoleManagerDTO roleManagerDTO = roleManagerDao.getByParam(role);
				if (roleManagerDTO == null) {
					throw new Exception("角色不存在");
				}
			}
		}
		// 验证数据，登录名，电话号码，邮箱，不能重复
		adminUserDao.save(user);
		if (roleIds != null && roleIds.size() > 0) {
			userToRoleDao.batchSave(user, roleIds);
		}
	}

	private void updateUserAndRole(AdminUser user, List<Integer> roleIds) throws Exception {
		// 验证数据，
		if (roleIds != null) {
			for (Integer roleId : roleIds) {
				RoleManager role = new RoleManager();
				role.setRoleId(roleId);
				RoleManagerDTO roleManagerDTO = 
						roleManagerDao.getByParam(role);
				if (roleManagerDTO == null) {
					throw new Exception("角色不存在");
				}
			}
		}
		if (user.getAdminUserId() != null) {// 保存
			adminUserDao.update(user);
			if (roleIds != null) {
				// 清空用户的所有角色，
				userToRoleDao.batchEmpty(user);
				if (roleIds.size() > 0) {
					// 再保存
					userToRoleDao.batchSave(user, roleIds);
				}
			}
		}
	}

	@Override
	public int getCountByParam(AdminUser adminUser) {
		return adminUserDao.getCountByParam(adminUser);
	}

	@Override
	public List<AdminUserDTO> getAllUser() {
		AdminUser user=new AdminUser();
		return adminUserDao.getListByParam(user);
	}

	@Override
	public AdminUserDTO getUser(String username, String password) {
		AdminUser user=new AdminUser();
		user.setLoginname(username);
		user.setPassword(password);
		return adminUserDao.getByParam(user);
	}

	@Override
	public AdminUserDTO getUser(String username) {
		AdminUser user=new AdminUser();
		user.setLoginname(username);
		return adminUserDao.getByParam(user);
	}

	@Override
	public void delete(Integer userId) {
		AdminUser user=new AdminUser();
		user.setAdminUserId(userId);
		user.setIsDelete(1);
		 adminUserDao.update(user);;
	}

	@Override
	public List<AdminUserDTO> getByPower(String powerCode) {
		return adminUserDao.getByPower(powerCode);
	}

	@Override
	public AdminUserDTO checkPower(String powerCode, Integer userId) {
		return adminUserDao.checkPower(powerCode, userId);
	}
}
