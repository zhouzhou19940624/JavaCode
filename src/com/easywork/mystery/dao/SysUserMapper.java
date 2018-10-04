package com.easywork.mystery.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.easywork.mystery.entity.SysUser;

import tk.mybatis.mapper.common.Mapper;

public interface SysUserMapper extends Mapper<SysUser> {

	@Select("select account from sys_user")
	public List<String> selectAccount();
}