package com.sys.pro.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sys.pro.model.TokenModel;

/**
 * @author JYB
 * @date 2018.10
 * @version 1.0
 * @parameter 过滤器
 * @return 返回值
 * @throws 异常类及抛出条件
 */
public interface TokenDao {
	
	@Insert("insert into t_token(id, val, expireTime, createTime, updateTime) values (#{id}, #{val}, #{expireTime}, #{createTime}, #{updateTime})")
	int save(TokenModel model);

	@Select("select * from t_token t where t.id = #{id}")
	TokenModel getById(String id);

	@Update("update t_token t set t.val = #{val}, t.expireTime = #{expireTime}, t.updateTime = #{updateTime} where t.id = #{id}")
	int update(TokenModel model);

	@Delete("delete from t_token where id = #{id}")
	int delete(String id);
}
