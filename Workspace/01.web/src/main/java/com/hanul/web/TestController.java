package com.hanul.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

	@RequestMapping("/first")
	public String first(Model model) {
//		비지니스로직
		String today = new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date());
		model.addAttribute("today", today);
//		응답화면연결
		return "index";
	}

	@RequestMapping("/second")
	public ModelAndView second() {
		SimpleDateFormat sdf = new SimpleDateFormat("hh시 mm분 ss초");
		String now = sdf.format(new Date());
		ModelAndView model = new ModelAndView();
		model.addObject("now", now);

		// 응답화면지정
		model.setViewName("index");
		return model;
	}

	@RequestMapping("/third")
	public String third(Model model) {
//		비지니스로직
		String today = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분 ss초").format(new Date());
		model.addAttribute("today", today);
//		응답화면연결
		return "now";
	}

	@RequestMapping("/member")
	public String memberJoin() {
		return "member/join";
	}

	@RequestMapping("joinRequest")
	public String joinRequest(HttpServletRequest request, Model model) {
		String name = request.getParameter("name");
		model.addAttribute("gender", request.getParameter("gender"));
		model.addAttribute("email", request.getParameter("email"));

		model.addAttribute("name", name);
		model.addAttribute("method", "HttpServletRequest 방식");
		return "member/info";
	}
}
