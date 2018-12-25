package com.sys.pro.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sys.pro.dto.LoginUser;
/**
 * @author JYB
 * @date 2018.10
 * @version 版本标识
 * @parameter 用户工具类
 * @return 返回值
 * @throws 异常类及抛出条件
 */
public class UserUtil {
	
	public static LoginUser getLoginUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			if (authentication instanceof AnonymousAuthenticationToken) {
				return null;
			}
			if (authentication instanceof UsernamePasswordAuthenticationToken) {
				return (LoginUser) authentication.getPrincipal();
			}
		}
		return null;		
	}
}
