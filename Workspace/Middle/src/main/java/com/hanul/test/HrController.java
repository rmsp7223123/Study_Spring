package com.hanul.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import test.hr.HrDAO;
import test.hr.HrVO;

@Controller
@RequestMapping("/hr")
public class HrController {
	
	@Autowired
	HrDAO dao;
	
	@Autowired
	@Qualifier("hr")
	SqlSession sql;
	
	@RequestMapping("/list")
	@ResponseBody
	public String list() {
		List<HrVO> list = sql.selectList("hr.list");
		return new Gson().toJson(list);
	}
}
