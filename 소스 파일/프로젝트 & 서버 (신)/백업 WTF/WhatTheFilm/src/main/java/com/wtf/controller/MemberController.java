package com.wtf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberController {

    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public ModelAndView showIndexPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("member/join"); // JSP 파일의 경로 설정
        return modelAndView;
    }
}