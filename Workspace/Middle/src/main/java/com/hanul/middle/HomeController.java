package com.hanul.middle;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import test.customer.CustomerServiceImpl;
import test.customer.CustomerVO;
import test.member.MemberDAO;
import test.member.MemberVO;

@RestController
public class HomeController {

	@Autowired
	private CustomerServiceImpl service;

	@Autowired
	SqlSession sql;

	@RequestMapping("/aa")
	public String aa(int id, String name) {
		CustomerVO vo = service.customer_info(id, name);
		CustomerVO a = sql.selectOne("test.info", vo);
		System.out.println(a);
		System.out.println(a.getName());
		return "" + a;
	}

	@RequestMapping("/aaa")
	public String aaa() {
		List<CustomerVO> a = sql.selectList("test.list");
		return "" + a;
	}

//	@RequestMapping(value = "/")
//	public String home(Locale locale, Model model, HttpServletResponse res, String userid) throws IOException {
//		int a = sql.selectOne("test.search");
//		System.out.println(a);
//		MemberVO vo = service.member_info(userid);
//		res.getWriter().println(a);
//		return "aaa";
//	}

}
