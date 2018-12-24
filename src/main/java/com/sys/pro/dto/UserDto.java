package com.sys.pro.dto;

import java.util.List;

import com.sys.pro.model.SysUser;

public class UserDto extends SysUser {

	private static final long serialVersionUID = -184009306207076712L;

	private List<Long> roleIds;

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

}
