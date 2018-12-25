package com.sys.pro.service;

import com.sys.pro.dto.LoginUser;
import com.sys.pro.dto.Token;

/**
 * @author JYB
 * @date 2018.10
 * @version 1.0
 * @parameter Token管理器
 * @see 可存储到redis或者数据库
 *      具体可看实现类
 *      	默认基于redis，实现类为 com.sys.pro.service.impl.TokenServiceJWTImpl
 *      	如要换成数据库存储，将TokenServiceImpl类上的注解@Primary挪到com.sys.pro.service.impl.TokenServiceDbImpl
 * @return 返回值
 * @throws 异常类及抛出条件
 */
public interface TokenService {

	Token saveToken(LoginUser loginUser);

	void refresh(LoginUser loginUser);

	LoginUser getLoginUser(String token);

	boolean deleteToken(String token);

}
