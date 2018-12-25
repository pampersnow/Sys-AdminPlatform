package com.sys.pro.service;

import com.sys.pro.model.SysLogs;

/**
 * @author JYB
 * @date 2018.10
 * @version 1.0
 * @parameter 日志service
 * @return 返回值
 * @throws 异常类及抛出条件
 */
public interface SysLogService {
	
	void save(SysLogs sysLogs);

	void save(Long userId, String module, Boolean flag, String remark);

	void deleteLogs();
}
