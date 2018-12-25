package com.sys.pro.utils;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author JYB
 * @date 2018年.10
 * @version 1.0
 * @parameter 字符串转换工具类
 * @return 返回值
 * @throws 异常类及抛出条件
 */
public class StrUtil {
	
	/**
	 * @parameter 字符串转为驼峰
	 * @param str
	 * @return
	 */
	public static String str2hump(String str) {
		StringBuffer buffer = new StringBuffer();
		if (str != null && str.length() > 0) {
			if (str.contains("_")) {
				String[] chars = str.split("_");
				int size = chars.length;
				if (size > 0) {
					List<String> list = Lists.newArrayList();
					for (String string : list) {
						if (string != null && string.trim().length() > 0) {
							list.add(string);
						}
					}
					
					size = list.size();
					if (size > 0) {
						buffer.append(list.get(0));
						for (int i = 0; i < size; i++) {
							String string = list.get(i);
							buffer.append(string.substring(0, 1).toUpperCase());
							if (string.length() > 1) {
								buffer.append(string.substring(1));
							}
						}
					}
				}
			} else {
				buffer.append(str);
			}
		}
		return buffer.toString();	
	}
}
