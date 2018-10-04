package com.easywork.mystery.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.easywork.mystery.entity.Data;
import tk.mybatis.mapper.common.Mapper;

public interface DataMapper extends Mapper<Data> {

	@Update("update data set create_time = #{createdate} where url_a = #{url}")
	public void update(@Param("createdate") String createdate, @Param("url") String url);

	@Select("select * from data where url_a like concat(concat('%',#{url},'%'))")
	public List<Data> selectFileByUrl(@Param("url") String url);

	@Update("update data set url_a = #{newUrl} where id = #{id}")
	public void updateDocument(@Param("id") Long id, @Param("newUrl") String newUrl);

	@Update("update data set filename = #{rename} where id = #{id}")
	public void updateFilename(@Param("id") long id, @Param("rename") String rename);

	/* 修改文件的模糊查询 */
	@Update("update data set url_a = replace(url_a,#{oldurl},#{newurl}) where userid = #{userid}")
	public void updateFF(@Param("oldurl") String oldurl, @Param("newurl") String newurl, @Param("userid") Long userid);

	public List<Data> getUrlAbyid(@Param("userid") long userid);

	@Update("update data set father_url = #{fatherurl} where id=#{i}")
	public void updateFatherUrlById(@Param("i") long i,@Param("fatherurl") String fatherurl);

}