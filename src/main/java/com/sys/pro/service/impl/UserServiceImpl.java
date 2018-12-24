package com.sys.pro.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sys.pro.dao.UserDao;
import com.sys.pro.dto.UserDto;
import com.sys.pro.model.SysUser;
import com.sys.pro.model.SysUser.Status;
import com.sys.pro.service.UserService;

/**
 * @author JYB 
 * @interfaceName：UserServiceImpl
 * @date 2018.10
 * @version 1.00
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger("adminLogger");

	@Autowired
	private UserDao userDao;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public SysUser saveUser(UserDto userDto) {
		// TODO Auto-generated method stub
		SysUser user = userDto;
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setStatus(Status.VALID);
		userDao.save(user);
		saveUserRoles(user.getId(), userDto.getRoleIds());
		log.debug("新增用户{}", user.getUsername());
		return user;
	}
	
	private void saveUserRoles(Long userId, List<Long> roleIds) {
		if (roleIds != null) {
			userDao.deleteUserRole(userId);
			if (!CollectionUtils.isEmpty(roleIds)) {
				userDao.saveUserRoles(userId, roleIds);
			}
		}
	}
	
	
	@Override
	public SysUser updateUser(UserDto userDto) {
		// TODO Auto-generated method stub
		userDao.update(userDto);
		saveUserRoles(userDto.getId(), userDto.getRoleIds());
		return userDto;
	}

	@Override
	public SysUser getUser(String username) {
		// TODO Auto-generated method stub
		return userDao.getUser(username);
	}

	@Override
	public void changePassword(String username, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		SysUser u = userDao.getUser(username);
		if (u == null) {
			throw new IllegalArgumentException("抱歉,用户不存在!");
		}
		
		if (!passwordEncoder.matches(oldPassword, u.getPassword())) {
			throw new IllegalArgumentException("抱歉,旧密码错误!");
		}
		
		userDao.changePassword(u.getId(), passwordEncoder.encode(newPassword));
		log.debug("修改"+username+"的密码");
	}

}
