package com.easywork.mystery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.easywork.mystery.dao.SysUserMapper;
import com.easywork.mystery.entity.SysUser;

@Transactional
@Service
public class SysUserService {
	@Autowired
	private SysUserMapper sysuserMapper;
	
	@CacheEvict(value="cache0", allEntries=true)
	public SysUserMapper getSysUserMapper() {
		return sysuserMapper;
	}
	
	@CacheEvict(value="cache0", allEntries=true)
	public int addUser(SysUser sysuser) {
		int result = sysuserMapper.insert(sysuser);
		return result;
	}
	
	public List<SysUser> getUser() {
		List<SysUser> list = sysuserMapper.selectAll();
		return list;
	}
	
	public SysUser getUser(String user) {
		Example exm = new Example(SysUser.class);
		Criteria crt = exm.createCriteria();
		crt.andCondition("account=", user);
		List<SysUser> list = sysuserMapper.selectByExample(exm);
		SysUser sysUser = list.get(0);
		return sysUser;
	}

	public List<String> getaccount() {
		return sysuserMapper.selectAccount();	 
	}

}