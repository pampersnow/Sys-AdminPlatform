package com.sys.pro.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author JYB
 * @date 2018.11
 * @version 1.0
 * @parameter 线程池配置、启用异步
 * @return 返回值
 * @throws 异常类及抛出条件
 */
@EnableAsync(proxyTargetClass = true)
@Configuration
public class AsycTaskExecutorConfig {

		public TaskExecutor taskExecutor() {
			ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
			taskExecutor.setCorePoolSize(50);
			taskExecutor.setMaxPoolSize(100);
			return taskExecutor;
		}
}
