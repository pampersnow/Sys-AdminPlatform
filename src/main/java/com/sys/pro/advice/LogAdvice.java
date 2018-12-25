package com.sys.pro.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.sys.pro.annotation.LogAnnotation;
import com.sys.pro.model.SysLogs;
import com.sys.pro.service.SysLogService;
import com.sys.pro.utils.UserUtil;

import io.swagger.annotations.ApiOperation;
/**
 * @author JYB
 * @date 2018.10
 * @version 1.0
 * @parameter 统一日志处理
 * @return 返回值
 * @throws 异常类及抛出条件
 */
public class LogAdvice {
	@Autowired
	private SysLogService logService;
	
	@Around(value = "@annotation(com.sys.pro.annotation.LogAnnotation)")
	public Object logSave(ProceedingJoinPoint joinPoint) throws Throwable {
		SysLogs sysLogs = new SysLogs();
        sysLogs.setUser(UserUtil.getLoginUser()); // 设置当前登录用户
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

		String module = null;
		LogAnnotation logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);
		module = logAnnotation.module();
		if (StringUtils.isEmpty(module)) {
			ApiOperation apiOperation = methodSignature.getMethod().getDeclaredAnnotation(ApiOperation.class);
			if (apiOperation != null) {
				module = apiOperation.value();
			}
		}

		if (StringUtils.isEmpty(module)) {
			throw new RuntimeException("没有指定日志module");
		}
		sysLogs.setModule(module);

		try {
			Object object = joinPoint.proceed();
			sysLogs.setFlag(true);

			return object;
		} catch (Exception e) {
			sysLogs.setFlag(false);
			sysLogs.setRemark(e.getMessage());
			throw e;
        } finally {
            if (sysLogs.getUser() != null) {
                logService.save(sysLogs);
            }
        }

	}
}
