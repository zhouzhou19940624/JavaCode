package com.easywork.mystery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.easywork.mystery.dao.SysEmpMapper;

@Transactional
@Service
public class SysEmpService {
	@Autowired
	private SysEmpMapper sysempMapper;

	public SysEmpMapper getSysEmpMapper() {
		return sysempMapper;
	}

}