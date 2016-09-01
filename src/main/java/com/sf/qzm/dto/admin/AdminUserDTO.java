package com.sf.qzm.dto.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sf.qzm.dto.SfDto;

public class AdminUserDTO implements SfDto{
	private Integer adminUserId;// 主键
	private String loginname;// 登录名
	private String password;// 登录密码
	
	private String nickname;// 昵称
	private String phone;// 电话 ：用于找回密码
	private String email;// email
	private String description;//备注
	private Integer status;// 状态，1-有效,0-冻结
	private Long createDate;// 创建的时间
	private AdminUserDTO createUserDTO;// 创建的管理员
	private Date updateDate;// 更新时间
	private AdminUserDTO updateUserDTO;// 更新操作的管理员
	
	private List<RoleManagerDTO> roles;// 用户对应的角色ID字符串。id,id 隔开的字符串
	private Map<String,String> menuCodeMap;//用户可以访问的菜单
	
	
	
	
	
	public Integer getAdminUserId() {
		return adminUserId;
	}
	public void setAdminUserId(Integer adminUserId) {
		this.adminUserId = adminUserId;
	}
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
	public AdminUserDTO getCreateUserDTO() {
		return createUserDTO;
	}
	public void setCreateUserDTO(AdminUserDTO createUserDTO) {
		this.createUserDTO = createUserDTO;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public AdminUserDTO getUpdateUserDTO() {
		return updateUserDTO;
	}
	public void setUpdateUserDTO(AdminUserDTO updateUserDTO) {
		this.updateUserDTO = updateUserDTO;
	}
	
	public List<RoleManagerDTO> getRoles() {
		return roles;
	}
	public void setRoles(List<RoleManagerDTO> roles) {
		this.roles = roles;
	}
	public Map<String, String> getMenuCodeMap() {
		return menuCodeMap;
	}
	public void setMenuCodeMap(Map<String, String> menuCodeMap) {
		this.menuCodeMap = menuCodeMap;
	}
	
}
