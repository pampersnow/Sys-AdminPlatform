package com.sys.pro.dto;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sys.pro.model.Permission;
import com.sys.pro.model.SysUser;

public class LoginUser extends SysUser implements UserDetails {

	private static final long serialVersionUID = -1379274258881257107L;

	private List<Permission> permissions;
	private String token;
	/** 登陆时间戳（毫秒） */
	private Long loginTime;
	/** 过期时间戳 */
	private Long expireTime;

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		// filter先过滤，map组成新的集合，collect什么样的集合...
		return permissions.parallelStream().filter(p -> !StringUtils.isEmpty(p.getPermission()))
				.map(p -> new SimpleGrantedAuthority(p.getPermission())).collect(Collectors.toSet());
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		// do nothing
	}

	// 账户是否未过期
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	// 账户是否未锁定
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return getStatus() != Status.LOCKED;
	}

	// 密码是否未过期
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	// 账户是否激活
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
