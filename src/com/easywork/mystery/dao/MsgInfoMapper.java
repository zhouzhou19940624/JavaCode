package com.easywork.mystery.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.easywork.mystery.entity.MsgInfo;

import tk.mybatis.mapper.common.Mapper;

public interface MsgInfoMapper extends Mapper<MsgInfo> {
	public int insertandgetprimarykey(MsgInfo info);

	@Select("select * from msg_info where id = #{msg}")
	public List<MsgInfo> selectMsgDetailById(long msg);
}