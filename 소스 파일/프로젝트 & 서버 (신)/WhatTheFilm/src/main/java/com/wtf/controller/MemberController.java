package com.wtf.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberController {

	@Autowired
	MemberService memberService;
	@Autowired
	MemberServiceImpl memberServiceImpl;
	// ȸ������
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView showRegisterPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("member/join"); // JSP ���� ��� �� ���ϸ�
		return modelAndView;
	}
	
	// ȸ�����Ա��
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerPost(@RequestParam Map<String, Object> map) {
		ModelAndView mav = new ModelAndView();
		
		

		// ���̵� �г��� �ߺ�Ȯ��
		String loginId = (String) map.get("loginid");
		String nickname = (String) map.get("nickname");

		boolean isDuplicateId = memberService.isLoginIdDuplicated(loginId);
		boolean isDuplicateNickname = memberService.isNicknameDuplicated(nickname);

		if (isDuplicateId) {
			mav.addObject("idMessage", "�ߺ��� ���̵�.");
			mav.setViewName("member/join"); // JSP
		} else if (isDuplicateNickname) {
			mav.addObject("nicknameMessage", "�ߺ��� �г���.");
			mav.setViewName("member/join"); // JSP
		} else {
			String memberId = memberService.create(map);
			mav.setViewName("redirect:/login");
		}

		return mav;
	}
    
	//�α���ȭ��
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("member/login"); // JSP ���� ��� �� ���ϸ�
		return modelAndView;
	}
	
	
	//�α���
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login_post(@RequestParam Map<String, Object> map, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		Map<String, Object> userpw = this.memberService.login_ok(map);
		System.out.println("login post");
		
		System.out.println(map.get("loginid"));
		System.out.println(map.get("password"));
		PasswordEncoder p = new BCryptPasswordEncoder();

		//System.out.println(p.matches(map.get("pw").toString(), userpw.get("pw").toString()));

		if (map.get("password") == null || (String) map.get("loginid") == null) {

			//���̵� ��й�ȣ �Է����� ������ �α��� â����
			mav.setViewName("redirect:/login"); //jsp

		} else {
			try {
				if (p.matches(map.get("password").toString(), userpw.get("password").toString())) {
					//�α��� ����
					HttpSession session = request.getSession();
					session.setAttribute("loginid", map.get("loginid"));
					mav.setViewName("redirect:/board");
					
				} else {
					//��ϵ� ���̵� ������ ȸ������ â����
					mav.setViewName("redirect:/register");
				}
			} catch (Exception e) {
				//����
				mav.setViewName("redirect:/index");
				return mav;
			}
			
		}

		return mav;

	}
	
    // �α׾ƿ� ó��
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/index"; // �α׾ƿ� �� �α��� �������� �����̷�Ʈ
    }
    //�� ����(�� ����)
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView detail(@RequestParam Map<String, Object> map, HttpServletRequest request) {


        ModelAndView mav = new ModelAndView();
       
		try {
			request.setCharacterEncoding("utf-8");
			HttpSession hs = request.getSession();
			String loginid = (String) hs.getAttribute("loginid");
			System.out.println("����̵� : " + loginid);
			map.put("loginid", loginid);
	        Map<String, Object> detailMap = this.memberService.detail(map);

			 System.out.println("1detailmap="+detailMap);
	        //String member_id = map.get("memberID").toString(); 
	        //mav.addObject("memberID", member_id);
			
			mav.addObject("loginid", loginid);
			System.out.println("id:"+loginid);
			
			mav.addObject("data", detailMap);	
			map.put("data", detailMap);
	        System.out.println("what???");
	        System.out.println(map);
			 System.out.println("2detailmap="+detailMap);
			//select detail �� ��������
			//�װŸ� data�ȿ� ����		
	        mav.setViewName("/member/detail");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return mav;
    }
   //���� ȭ��
    @RequestMapping(value="/update", method = {RequestMethod.GET}) 
    public ModelAndView update(@RequestParam Map<String, Object> map) {
    	System.out.println("�ž� : " + map);

		ModelAndView mav = new ModelAndView();
		
    	Map<String, Object> detailMap = new HashMap<String, Object>();
    	detailMap.put("myloginid", map.get("loginid"));
    	detailMap.put("oldnickname", map.get("nickname"));
    	detailMap.put("oldbirthyear", map.get("birthyear"));
    	//System.out.println("�����ϸ� : " + detailMap);
		mav.addObject("data", detailMap);
		mav.setViewName("/member/update");
		return mav;
    }
    //���� ���
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView updatePost(@RequestParam Map<String, Object> map) {

		ModelAndView mav = new ModelAndView();
		System.out.println("mappp: " + map);
		//System.out.println(mav);
		boolean isUpdateSuccess = this.memberService.edit(map);
		System.out.println("�������� : " + isUpdateSuccess);
		if (isUpdateSuccess) {
			String loginid = map.get("loginid").toString();
			System.out.println(loginid);
	    	Map<String, Object> detailMap = this.memberService.detail(map);
	    	System.out.println("������-�� : " + detailMap);
			mav.addObject("data", detailMap);
			mav.setViewName("/member/detail");
		} else {
			mav = this.update(map);
		}

		return mav;
	}
    
}
    
