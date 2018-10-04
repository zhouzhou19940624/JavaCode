package com.easywork.mystery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easywork.mystery.dao.MsgCommMapper;
import com.easywork.mystery.dao.MsgInfoMapper;
import com.easywork.mystery.entity.MsgInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Transactional
@Service
public class MsgInfoService {
	@Autowired
	private MsgInfoMapper msginfoMapper;
	@Autowired
	private MsgCommMapper msgcommMapper;
	

	public MsgInfoMapper getMsgInfoMapper() {
		return msginfoMapper;
	}

	public int addMsgDetil(MsgInfo info) {
		msginfoMapper.insertandgetprimarykey(info);
		Long id = info.getId();
		System.out.println("发送信息回显信息detil主键"+ id);
		System.out.println(info.getTitle());
		int result= msgcommMapper.updatemsgid(id,info.getTitle());
		return result;
	}

	public List<MsgInfo> selectMsgDetail(String msgid) {
		long msg = Long.parseLong(msgid);
		List<MsgInfo> list = msginfoMapper.selectMsgDetailById(msg);
		return list;
	}

	public int deleteMsgById(Long msgid) {
		int result = msginfoMapper.deleteByPrimaryKey(msgid);
		return result;
	}

	public String insertMsg(String msg, String title) {
		if(msg.equals(""))
			return "error";
		if(title.equals(""))
			return "error";
		int state = 1;
		MsgInfo info = new MsgInfo(title,state,msg);
		msginfoMapper.insertandgetprimarykey(info);
		Long key = info.getId();
		String k = String.valueOf(key);
		return k;
	}
}