package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.admin.AdminUser;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.admin.AdminUserDTO;

/**
 *
 * @author Administrator
 *
 */
public interface AdminUserDao {
	void save(@Param("user")AdminUser user);//增
	void update(@Param("user")AdminUser user);//改
	List<AdminUserDTO> getListByParam(@Param("user")AdminUser user);//查询数组
	List<AdminUserDTO> getListByPage(@Param("page")PageDTO<AdminUser> page);//查询数组
	AdminUserDTO getByParam(@Param("user")AdminUser user);//查询单个
	int getCountByParam(@Param("user")AdminUser user);//查询数量
	int checkUserCount(@Param("user")AdminUser user);
	AdminUserDTO mengHuLogin(@Param("user")AdminUser user);//门户登陆
	
	List<AdminUserDTO> getListByParamAndRole(@Param("user")AdminUser user,
			@Param("roleId")Integer roleId);//查询数组
	
	
}
