package com.sys.pro.service.impl;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sys.pro.dao.SysLogsDao;
import com.sys.pro.model.SysLogs;
import com.sys.pro.model.SysUser;
import com.sys.pro.service.SysLogService;

/**
 * @author JYB
 * @date 2018.10
 * @version 1.0
 * @parameter 日志实现类
 * @return 返回值
 * @throws 异常类及抛出条件
 */
@Service
public class SysLogServiceImpl implements SysLogService {

	private static final Logger log = LoggerFactory.getLogger("adminLogger");

	@Autowired
	private SysLogsDao sysLogsDao;

	/**
	 * @parameter 将该方法改为异步,用户由调用者设置
	 * @param sysLogs
	 * @see com.sys.pro.advice.LogAdvice
	 * @see com.sys.pro.service.SysLogService#deleteLogs()
	 * @see com.sys.pro.service.SysLogService#save(java.lang.Long, java.lang.String,
	 *      java.lang.Boolean, java.lang.String)
	 */
	@Async // 异步调用注解
	@Override
	public void save(SysLogs sysLogs) {
		// TODO Auto-generated method stub
		if (sysLogs == null || sysLogs.getUser() == null || sysLogs.getUser().getId() == null) {
			return;
		}
		sysLogsDao.save(sysLogs);
	}

	@Override
	public void save(Long userId, String module, Boolean flag, String remark) {
		// TODO Auto-generated method stub
		SysLogs sysLogs = new SysLogs();
		sysLogs.setFlag(flag);
		sysLogs.setModule(module);
		sysLogs.setRemark(remark);

		SysUser user = new SysUser();
		user.setId(userId);
		sysLogs.setUser(user);

		sysLogsDao.save(sysLogs);
	}

	@Override
	public void deleteLogs() {
		// TODO Auto-generated method stub
		Date date = DateUtils.addMonths(new Date(), -3);
		String time = DateFormatUtils.format(date, DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.getPattern());

		int n = sysLogsDao.deleteLogs(time);
		log.info("删除"+time+"之前日志"+n+"条");
	}

}
