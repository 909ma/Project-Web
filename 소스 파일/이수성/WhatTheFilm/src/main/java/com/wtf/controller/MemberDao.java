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
	//회원가입
	public int insert(Map<String, Object> map) {
		return this.sqlSessionTemplate.insert("member.insert", map);
	}
	//아이디 확인
	public int countByLoginId(String loginId) {
		return this.sqlSessionTemplate.selectOne("member.countByLoginId", loginId);
	}
	//닉네임 확인
	public int countByNickname(String nickname) {
		return this.sqlSessionTemplate.selectOne("member.countByNickname", nickname);
	}
	//로그인
	public Map<String, Object> Login_Check(Map<String, Object> map){
		 return this.sqlSessionTemplate.selectOne("member.login_ok", map);		 
	 }
	//마이페이지
	public Map<String, Object> selectDetail(Map<String, Object> map) {
		System.out.println("member dao map : " + map);
		return this.sqlSessionTemplate.selectOne("member.select_detail", map);
	}
	//정보 수정
	public int update(Map<String, Object> map) {  
		System.out.println("member 다오2 : " +map);
		 return this.sqlSessionTemplate.update("member.update", map);  
		 }  

}
	



