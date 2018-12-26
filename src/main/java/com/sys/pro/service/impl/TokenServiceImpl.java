package com.sys.pro.service.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import com.sys.pro.dto.LoginUser;
import com.sys.pro.dto.Token;
import com.sys.pro.service.SysLogService;
import com.sys.pro.service.TokenService;

/**
 * @author JYB
 * @date 2018.10
 * @version 1.0
 * @parameter token存到redis的实现类  普通token，uuid
 * @return 返回值
 * @throws 异常类及抛出条件
 */
@Deprecated
//@Service
public class TokenServiceImpl implements TokenService {

	/**
	 * token过期秒数
	 */
	@Value("${token.expire.seconds}")
	private Integer expireSeconds;
	@Autowired
	private RedisTemplate<String, LoginUser> redisTemplate;
	@Autowired
	private SysLogService logService;
	
	@Override
	public Token saveToken(LoginUser loginUser) {
		// TODO Auto-generated method stub
		String token = UUID.randomUUID().toString();

		loginUser.setToken(token);
		cacheLoginUser(loginUser);
		// 登陆日志
		logService.save(loginUser.getId(), "登陆", true, null);

		return new Token(token, loginUser.getLoginTime());
	}

	private void cacheLoginUser(LoginUser loginUser) {
		loginUser.setLoginTime(System.currentTimeMillis());
		loginUser.setExpireTime(loginUser.getLoginTime() + expireSeconds * 1000);
		// 缓存
		redisTemplate.boundValueOps(getTokenKey(loginUser.getToken())).set(loginUser, expireSeconds, TimeUnit.SECONDS);
	}
	
	@Override
	public void refresh(LoginUser loginUser) {
		// TODO Auto-generated method stub
		cacheLoginUser(loginUser);
	}

	@Override
	public LoginUser getLoginUser(String token) {
		// TODO Auto-generated method stub
		return redisTemplate.boundValueOps(getTokenKey(token)).get();
	}

	@Override
	public boolean deleteToken(String token) {
		// TODO Auto-generated method stub
		String key = getTokenKey(token);
		LoginUser loginUser = redisTemplate.opsForValue().get(key);
		if (loginUser != null) {
			redisTemplate.delete(key);
			// 退出日志
			logService.save(loginUser.getId(), "退出", true, null);

			return true;
		}

		return false;
	}
	private String getTokenKey(String token) {
		return "tokens:" + token;
	}
}
