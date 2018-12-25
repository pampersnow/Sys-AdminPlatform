package com.sys.pro.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;

/**
 * @author JYB
 * @date 2018.10
 * @version 1.0
 * @parameter spring获取bean工具类
 * @return 返回值
 * @throws 异常类及抛出条件
 */
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		SpringUtil.applicationContext = applicationContext;
	}

	public static <T> T getBean(Class<T> cla) {
		return applicationContext.getBean(cla);
	}

	public static <T> T getBean(String name, Class<T> cla) {
		return applicationContext.getBean(name, cla);
	}

	public static String getProperty(String key) {
		return applicationContext.getBean(Environment.class).getProperty(key);
	}
}
