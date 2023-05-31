package com.hanul.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {
	@RequestMapping("/login")
	public String login() {
		return "member/login";
	}

	@RequestMapping("loginResult")
	public String loginResult(@RequestParam String id, String pw) {
		if (id.equals("문병준") && pw.equals("123")) {
//			return "home";// forward방식
			return "redirect:/";
		} else {
			return "redirect:login"; // redirect방식
		}
	}
}
