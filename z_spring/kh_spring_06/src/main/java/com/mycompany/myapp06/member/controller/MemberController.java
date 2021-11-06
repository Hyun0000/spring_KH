package com.mycompany.myapp06.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.myapp06.member.model.service.MemberService;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	// public String
	// import org.springframework.web.servlet.ModelAndView;
	@RequestMapping(value = "memberlist", method = RequestMethod.GET)
	// method = RequestMethod만 쓰면 --> 겟이든 포스트든 다 여기로 오면돼
	// 근데 이런 경우는 거의 없다.
	public ModelAndView memberList(ModelAndView mv) {
		// mv.setViewName("a/b");
		mv.setViewName("memberlist");
		mv.addObject("mlist", memberService.selectMembers());
		return mv;
	}
}
