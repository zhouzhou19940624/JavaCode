package com.easywork.mystery.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.easywork.mystery.entity.MsgComm;

import tk.mybatis.mapper.common.Mapper;

public interface MsgCommMapper extends Mapper<MsgComm> {

	
	
    @Update("update msg_comm set msgId =#{msgId} where msgTitle=#{title}")
	public int updatemsgid(@Param("msgId")Long msgId, @Param("title") String title);
        
    @Update("update msg_comm set readflag=#{readflag} where msgId=#{msg}")
	public int changeReadFlag(@Param("msg") Long msg,@Param("readflag") int readflag);

    @Delete("delete from msg_comm where msgId=#{msg_id}")
	public int deleteByMsgId(@Param("msg_id") long msg_id);

    @Select("select readflag from msg_comm where msgId=#{id}")
	public int getReadflag(@Param("id") long id);
}