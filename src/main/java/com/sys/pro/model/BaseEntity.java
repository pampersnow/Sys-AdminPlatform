package com.sys.pro.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author JYB
 * @date 2018.10
 * @explain 公共抽象类>>扩展序列化字段
 */
public abstract class BaseEntity<ID extends Serializable> implements Serializable{

	private static final long serialVersionUID = 2054813493011812469L;

	private ID id;
	private Date createTime = new Date();
	private Date updateTime = new Date();
	public ID getId() {
		return id;
	}
	public void setId(ID id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
