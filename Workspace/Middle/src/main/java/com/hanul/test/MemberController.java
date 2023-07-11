package com.hanul.test;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import test.andmembers.AndMembersDAO;
import test.andmembers.AndMembersVO;

@Controller
public class MemberController {

	@Autowired
	SqlSession sql;

	@Autowired
	AndMembersDAO dao;

	@RequestMapping(value = "/login", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String login(String id, String pw) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("pw", pw);
		AndMembersVO vo = dao.and_info2(params);
		Gson gson = new Gson();
		return gson.toJson(vo);
	}

}
