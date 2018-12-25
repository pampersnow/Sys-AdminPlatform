package com.sys.pro.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author JYB
 * @date 2018.10
 * @version 1.0
 * @parameter spring security配置
 * @return 返回值
 * @throws 异常类及抛出条件
 */
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
}
