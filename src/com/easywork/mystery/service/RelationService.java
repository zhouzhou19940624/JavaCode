package com.easywork.mystery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easywork.mystery.dao.RelationMapper;
import com.easywork.mystery.entity.Relation;

@Transactional
@Service
public class RelationService {
	@Autowired
	private RelationMapper relationMapper;

	public RelationMapper getRelationMapper() {
		return relationMapper;
	}

	public String checkRelation(String account, String friend) {
		String result = relationMapper.checkRelation(account,friend);
		return result;	
	}

	public int addRelation(String account, String friend) {
		return relationMapper.addRelation(account,friend);
	}

	public List<String> selectFriends(String account) {
		return relationMapper.selectFriends(account);
	}

	public int deleteFriend(String account, String friend_account) {
		return relationMapper.deleteFriend(account,friend_account);
	}

}