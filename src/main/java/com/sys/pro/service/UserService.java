package com.sys.pro.service;

import com.sys.pro.dto.UserDto;
import com.sys.pro.model.SysUser;

/**
 * @author JYB 
 * @interfaceName： UserService
 * @date 2018.10
 * @version 1.00
 */
public interface UserService {
	
	SysUser saveUser(UserDto userDto);

	SysUser updateUser(UserDto userDto);

	SysUser getUser(String username);

	void changePassword(String username, String oldPassword, String newPassword);
}
