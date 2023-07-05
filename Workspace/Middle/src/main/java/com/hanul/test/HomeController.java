package com.hanul.test;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import test.customer.CustomerVO;

@RestController
public class HomeController {

//	json / xml
//	json => String으로 되어있는데 key와 value가 존재하고 list같은 자료구조도 []등으로 표현이 가능한 데이터 타입
//	요소 하나 (Object, DTO) ==> 기호 : {} , List ==> [],
//	[{vo}....{vo.lastindex}]

	@Autowired
	SqlSession sql;

	@RequestMapping(value = "/list.cu", produces = "text/html; charset=utf-8")
	public String home() {
		System.out.println("확인용");
		List<CustomerVO> list = sql.selectList("test.list");
		Gson gson = new Gson();
		return gson.toJson(list);
	}

	@RequestMapping(value = "/obj.cu", produces = "text/html; charset=utf-8")
	public String obj() {
		CustomerVO vo = new CustomerVO();
		vo.setEmail("이메일확인용99");
		vo.setName("이름확인용99");
		return new Gson().toJson(vo);
	}

}
