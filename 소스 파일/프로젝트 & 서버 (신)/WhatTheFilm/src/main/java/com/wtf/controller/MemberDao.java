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
	//�A���a��
	public int insert(Map<String, Object> map) {
		return this.sqlSessionTemplate.insert("member.insert", map);
	}
	//�a���� �·�
	public int countByLoginId(String loginId) {
		return this.sqlSessionTemplate.selectOne("member.countByLoginId", loginId);
	}
	//���A�� �·�
	public int countByNickname(String nickname) {
		return this.sqlSessionTemplate.selectOne("member.countByNickname", nickname);
	}
	//���a��
	public Map<String, Object> Login_Check(Map<String, Object> map){
		 return this.sqlSessionTemplate.selectOne("member.login_ok", map);		 
	 }
	//�a���A����
	public Map<String, Object> selectDetail(Map<String, Object> map) {
		System.out.println("member dao map : " + map);
		return this.sqlSessionTemplate.selectOne("member.select_detail", map);
	}
	//���� ����
	public int update(Map<String, Object> map) {  
		System.out.println("member �a��2 : " +map);
		 return this.sqlSessionTemplate.update("member.update", map);  
		 }  

}
	



