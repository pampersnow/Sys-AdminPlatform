package com.sys.pro.service.impl;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.sys.pro.dto.LoginUser;
import com.sys.pro.dto.Token;
import com.sys.pro.service.SysLogService;
import com.sys.pro.service.TokenService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author JYB
 * @date 2018.10
 * @version 1.0
 * @parameter token存到redis的实现类 jwt实现的token
 * @return 返回值
 * @throws 异常类及抛出条件
 */
@Primary
@Service
public class TokenServiceJWTImpl implements TokenService {

	private static final Logger log = LoggerFactory.getLogger("adminLogger");

	// token过期毫秒数
	@Value("${token.expire.seconds}")
	private Integer expireSeconds;
	// 私钥
	@Value("${token.jwtSecret}")
	private String jwtSecret;

	@Autowired
	private RedisTemplate<String, LoginUser> redisTemplate;
	@Autowired
	private SysLogService logService;

	private static Key KEY = null;
	private static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";

	@Override
	public Token saveToken(LoginUser loginUser) {
		// TODO Auto-generated method stub
		loginUser.setToken(UUID.randomUUID().toString());
		cacheLoginUser(loginUser);
		logService.save(loginUser.getId(), "登录", true, null);
		String jwtToken = createJWTToken(loginUser);
		return new Token(jwtToken, loginUser.getLoginTime());
	}

	/**
	 * @parameter 生成jwt
	 * @param loginUser
	 * @return
	 */
	@SuppressWarnings("unused")
	private String createJWTToken(LoginUser loginUser) {
		Map<String, Object> claims = new HashMap<>();
		// 放入一个随机字符串，通过该串可找到登陆用户
		claims.put(LOGIN_USER_KEY, loginUser.getToken());
		String jwtToken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance())
				.compact();
		return jwtToken;
	}
	/**
	 * @parameter 缓存登录用户信息
	 * @param loginUser
	 */
	@SuppressWarnings("unused")
	private void cacheLoginUser(LoginUser loginUser) {
		loginUser.setLoginTime(System.currentTimeMillis());
		loginUser.setExpireTime(loginUser.getLoginTime() + expireSeconds*1000);
		// 根据uuid将loginUser缓存
		redisTemplate.boundValueOps(getTokenKey(loginUser.getToken())).set(loginUser, expireSeconds, TimeUnit.SECONDS);		
	}
	
	/*
	 * @parameter 缓存登录用户信息
	 * @see com.sys.pro.service.TokenService#refresh(com.sys.pro.dto.LoginUser)
	 */
	@Override
	public void refresh(LoginUser loginUser) {
		// TODO Auto-generated method stub
		cacheLoginUser(loginUser);
	}
	
	@Override
	public LoginUser getLoginUser(String token) {
		// TODO Auto-generated method stub
		String uuid = getUUIDFromJWT(token);
		if (uuid != null) {
			return redisTemplate.boundValueOps(getTokenKey(uuid)).get();
		}
		return null;
	}

	@Override
	public boolean deleteToken(String token) {
		// TODO Auto-generated method stub
		String uuid = getUUIDFromJWT(token);
		if (uuid != null) {
			String key = getTokenKey(uuid);
			LoginUser loginUser = redisTemplate.opsForValue().get(key);
			if (loginUser != null) {
				redisTemplate.delete(key);
				//退出日志
				logService.save(loginUser.getId(),"退出", true, null);
				return true;
			}
		}
		return false;
	}
	private String getTokenKey (String uuid){
		return "tokens" + uuid;
	}
	private Key getKeyInstance() {
		if (KEY == null) {
			synchronized (TokenServiceJWTImpl.class) {
				//双重锁
				if (KEY == null) {
					byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
					KEY = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
				}
			}
		}
		return KEY;
	}
	
	private String getUUIDFromJWT(String jwtToken) {
		if ("null".equals(jwtToken) || StringUtils.isBlank(jwtToken)) {
			return null;
		}

		try {
			Map<String, Object> jwtClaims = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwtToken).getBody();
			return MapUtils.getString(jwtClaims, LOGIN_USER_KEY);
		} catch (ExpiredJwtException e) {
			log.error("{}已过期", jwtToken);
		} catch (Exception e) {
			log.error("{}", e);
		}
		return null;
	}
}
