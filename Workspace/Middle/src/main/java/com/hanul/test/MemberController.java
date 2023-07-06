package com.hanul.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
	
	@RequestMapping(value = "/login", produces = "text/html; charset=utf-8")
	public String login(String id, String pw) {
		if(id.equals("admin") && pw.equals("admin1234")) {
			System.out.println("Y");
			return "Y";
		} else {
			System.out.println("N");
			return "N";
		}
		
	}
}
