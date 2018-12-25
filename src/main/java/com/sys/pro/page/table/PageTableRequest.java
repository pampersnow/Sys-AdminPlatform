package com.sys.pro.page.table;

import java.io.Serializable;
import java.util.Map;

/**
 * @author JYB
 * @date 2018.10
 * @version 版本标识
 * @parameter 分页查询参数
 * @return 
 * @throws 
 */
public class PageTableRequest implements Serializable{

	private static final long serialVersionUID = 7328071045193618467L;
		
	private Integer offset;
	private Integer limit;
	private Map<String, Object> params;
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
