package com.hanul.middle;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@Autowired // 스프링 빈 객체끼리 초기화 할 때 필요한게 있다면 자동으로 필요한 내용을 주입해서 초기화시킴
	@Qualifier("hanul")
	SqlSession sql;

	@Autowired
	TestDAO dao;

	@Autowired
	@Qualifier("tt")
	TestVO vo;

	@RequestMapping(value = "/")
	public String home(Locale locale, Model model) {
//		int result = sql.selectOne("test.dual");
//		System.out.println(result);
		System.out.println(vo.getField1());
		dao.select();

		return "home";
	}

}
