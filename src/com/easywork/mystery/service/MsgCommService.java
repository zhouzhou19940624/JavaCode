package com.easywork.mystery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easycore.utils.MyPage;
import com.easywork.mystery.dao.MsgCommMapper;
import com.easywork.mystery.entity.MsgComm;
import com.easywork.mystery.entity.SysUser;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;



@Transactional
@Service
public class MsgCommService {
	@Autowired
	private MsgCommMapper msgcommMapper;

	public MsgCommMapper getMsgCommMapper() {
		return msgcommMapper;
	}

	public int addcomm(MsgComm msgc) {
		return msgcommMapper.insert(msgc);
	}

	/*查找所有信息*/
	public MyPage<MsgComm> queryMsg(SysUser user,int pageNum,int pageSize) {
		String account = user.getAccount();
		Example exm = new Example(MsgComm.class);
		Criteria ctr = exm.createCriteria();
		ctr.andCondition("receiverAccount=", account);
		exm.setOrderByClause("readflag");
		MyPage<MsgComm> mypage = MyPage.selectByExampleAndPage(msgcommMapper, exm, pageNum, pageSize);
		return mypage;
	}

	/*改变状态*/
	public int changeReadFlag(String msgid) {
		long msg = Long.parseLong(msgid);
		int readflag = 1;
		int result = msgcommMapper.changeReadFlag(msg,readflag);
		return result;
	}

	public int deleteMsgByMsgId(long msg_id) {
		return msgcommMapper.deleteByMsgId(msg_id);	
	}

	public void changeRead(long id) {
		int a = 0;
		int b = 1;
		int result = msgcommMapper.getReadflag(id);
		if(result==1)
			msgcommMapper.changeReadFlag(id, a);
		if(result==0)
			msgcommMapper.changeReadFlag(id, b);
	}



}