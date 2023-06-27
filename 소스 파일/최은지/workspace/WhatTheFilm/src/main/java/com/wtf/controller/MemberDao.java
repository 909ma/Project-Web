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
	//?٬ٯ�:�Vٮ??
	public int insert(Map<String, Object> map) {
		return this.sqlSessionTemplate.insert("member.insert", map);
	}
	//��ܡ???�3 ?�<ܬݘ?��??
	public int countByLoginId(String loginId) {
		return this.sqlSessionTemplate.selectOne("member.countByLoginId", loginId);
	}
	//٥ܢݚ�O?? ?�<ܬݘ?��??
	public int countByNickname(String nickname) {
		return this.sqlSessionTemplate.selectOne("member.countByNickname", nickname);
	}
	//�4?�N�O?? �N?٥?�N٬????�N?
	public Map<String, Object> Login_Check(Map<String, Object> map){
		 return this.sqlSessionTemplate.selectOne("member.login_ok", map);
		 
	 }
	//�A������ ����
	public  int editUser(Map<String, Object> map) {
		return this.sqlSessionTemplate.update("member.edit_user", map);
	}

}
	



