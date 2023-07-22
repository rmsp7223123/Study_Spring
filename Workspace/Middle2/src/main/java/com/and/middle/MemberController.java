package com.and.middle;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import member.AndMemberDAO;
import member.AndMemberVO;

@RestController @RequestMapping("/member")
public class MemberController {
	//1. 크롬창을 이용해서 url Get방식으로 login처리가 "Y"와 "N"이 되는지확인.
	// ↑
	//2. Android에서 되는지 확인 <- And00_Login 프로젝트를 생성 후 개발환경 직접 구축.
	//3. Android에서 Edittext를 이용해서 되는지 확인.
	@Autowired AndMemberDAO dao;
	@RequestMapping(value = "/login" , produces = "text/html;charset=utf-8")
	public String login(String id , String pw) {
		//AndMemberDAO dao = new AndMemberDAO();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("pw", pw);
		AndMemberVO vo =  dao.login(params);
		return new Gson().toJson(vo);
		
	}
}
