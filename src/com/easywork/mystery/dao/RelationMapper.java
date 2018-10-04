package com.easywork.mystery.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.easywork.mystery.entity.Relation;

import tk.mybatis.mapper.common.Mapper;

public interface RelationMapper extends Mapper<Relation> {
	/* 检查好友关系 */
	@Select("select id from relation where account=#{account} and friend_account=#{friend}")
	public String checkRelation(@Param("account") String account, @Param("friend") String friend);
    /* 增加好友关系*/
	@Insert("insert into relation(account,friend_account) values(#{account},#{friend})")
	public int addRelation(@Param("account") String account, @Param("friend") String friend);
     /*查询所有好友*/
	@Select("select friend_account from relation where account = #{account}")
	public List<String> selectFriends(@Param("account") String account);
    /*删除好友关系*/
	@Delete("delete from relation where account = #{account} and friend_account = #{friend_account}")
	public int deleteFriend(@Param("account") String account, @Param("friend_account") String friend_account);
}