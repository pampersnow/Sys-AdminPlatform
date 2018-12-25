package com.sys.pro.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

/**
 * @author JYB
 * @date 2018.10
 * @version 1.0
 * @parameter ResponseUtil
 * @return 返回值
 * @throws 异常类及抛出条件
 */
public class ResponseUtil {
	
	public static void responseJson(HttpServletResponse response, int status, Object data) {
		// TODO Auto-generated method stub
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "*");
			response.setContentType("application/json;charset=UTF-8");
			response.setStatus(status);
			
			response.getWriter().write(JSONObject.toJSONString(data));
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
