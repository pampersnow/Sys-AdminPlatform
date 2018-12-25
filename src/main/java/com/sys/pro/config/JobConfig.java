package com.sys.pro.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author JYB
 * @date 2018.11
 * @version 1.0
 * @parameter 任务调度
 * @return 返回值
 * @throws 异常类及抛出条件
 */
@Configuration
public class JobConfig {

	public static final String KEY = "applicationContextSchedulerContextKey";

	@Bean("adminQuartzScheduler")
	public SchedulerFactoryBean quartzScheduler(DataSource dataSource) throws Exception{

		SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();

		try {
			quartzScheduler.setQuartzProperties(
					PropertiesLoaderUtils.loadProperties(new ClassPathResource("quartz.properties")));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		quartzScheduler.setDataSource(dataSource);
		quartzScheduler.setOverwriteExistingJobs(true);
		quartzScheduler.setApplicationContextSchedulerContextKey(KEY);
		quartzScheduler.setStartupDelay(10);
		return quartzScheduler;
	}
}
