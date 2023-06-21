package com.wtf.controller;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {
	
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	//ȸ������
	public int insert(Map<String, Object> map) {
		return this.sqlSessionTemplate.insert("member.insert", map);
	}
	//���̵� �ߺ�Ȯ��
	public int countByLoginId(String loginId) {
		return this.sqlSessionTemplate.selectOne("member.countByLoginId", loginId);
	}
	//�г��� �ߺ�Ȯ��
	public int countByNickname(String nickname) {
		return this.sqlSessionTemplate.selectOne("member.countByNickname", nickname);
	}
	//�α��� ��ɱ����ϱ�
	public Map<String, Object> Login_Check(Map<String, Object> map){
		 return this.sqlSessionTemplate.selectOne("member.login_ok", map);
		 
	 }

}
	



