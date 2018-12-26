package com.sys.pro.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sys.pro.dto.LoginUser;
import com.sys.pro.service.TokenService;

/**
 * @author JYB
 * @date 2018.10
 * @version 1.0
 * @parameter Token过滤器
 * @return 返回值
 * @throws 异常类及抛出条件
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

	private static final String TOKEN_KEY = "token";

	@Autowired
	private TokenService tokenService;
	@Autowired
	private UserDetailsService userDetailsService;
	private static final Long MINUTES_10 = 10 * 60 * 1000L;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String token = getToKen(request);
		if (StringUtils.isNotBlank(token)) {
			LoginUser loginUser = tokenService.getLoginUser(token);
			if (loginUser != null) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						loginUser, null, loginUser.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * @parameter 校验时间
	 *            过期时间与当前时间对比，临近过期10分钟内的话，自动刷新缓存
	 * @param loginUser
	 * @return
	 */
	@SuppressWarnings("unused")
	private LoginUser checkLoginTime(LoginUser loginUser) {
		Long expireTime = loginUser.getExpireTime();
		long currentTimeMillis = System.currentTimeMillis();
		if (expireTime - currentTimeMillis <= MINUTES_10) {
			String token = loginUser.getToken();
			loginUser = (LoginUser) userDetailsService.loadUserByUsername(loginUser.getUsername());
			loginUser.setToken(token);
			tokenService.refresh(loginUser);
		}
		return loginUser;
	}

	/**
	 * @parameter 根据参数或者header获取token
	 * @param request
	 * @return
	 */
	public static String getToKen(HttpServletRequest request) {
		String token = request.getParameter(TOKEN_KEY);
		if (StringUtils.isBlank(token)) {
			token = request.getHeader(TOKEN_KEY);
		}
		return token;
	}
}
