package com.wtf.controller;

import java.lang.reflect.Member;
import java.util.Map;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    //ȸ������
    @Override
    public String create(Map<String, Object> map) {

        // ���̵�� �г��� �ߺ� �˻�
        String loginId = (String) map.get("loginid");
        String nickname = (String) map.get("nickname");
        int loginIdCount = memberDao.countByLoginId(loginId);
        int nicknameCount = memberDao.countByNickname(nickname);
        
        if (loginIdCount > 0) {
            // ���̵� �ߺ��� ���
            return "���̵� �̹� ��� ���Դϴ�.";
        }
        
        if (nicknameCount > 0) {
            // �г����� �ߺ��� ���
            return "�г����� �̹� ��� ���Դϴ�.";
        }

        // ��й�ȣ ��ȣȭ
        String password = (String) map.get("password");
        String encodedPassword = passwordEncoder.encode(password);
        map.put("password", encodedPassword);
        

        int affectRowCount = this.memberDao.insert(map);
        if (affectRowCount == 1) {
            return map.get("member_id").toString();
        }
        return null;
    }
    //���̵� �ߺ�Ȯ��
    @Override
    public boolean isLoginIdDuplicated(String loginId) {
        int count = memberDao.countByLoginId(loginId);
        return count > 0;
    }
    //�г��� �ߺ�Ȯ��
    @Override
    public boolean isNicknameDuplicated(String nickname) {
        int count = memberDao.countByNickname(nickname);
        return count > 0;
    }
    //�α���
	@Override
	public boolean login(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return true;
	}
	//�α���
	@Override
	public Map<String, Object> login_ok(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.memberDao.Login_Check(map);
	}
   // ����������
	@Override
	public Map<String, Object> detail(Map<String, Object> map) {
		
		System.out.println("�����������  ������ = " + map);
		return this.memberDao.selectDetail(map);
	}
	
	@Override  
	public boolean edit(Map<String, Object> map) {  
		System.out.println("member ���� ���� : " +map);
	int affectRowCount = this.memberDao.update(map);  
	return affectRowCount == 1;  

	}  
	

}