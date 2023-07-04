package com.hanul.middle;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import test.notice.NoticeDAO;
import test.notice.NoticeVO;

@RestController
public class NoticeController {

	@Autowired
	SqlSession sql;

	@Autowired
	NoticeDAO service;

	@RequestMapping("/one.nt")
	public String a(int id) {

		NoticeVO vo = sql.selectOne("test.find", id);
		System.out.println(vo.getName());

		return "" + vo.getContent();
	}
	
	@RequestMapping("/list.nt")
	public String aa() {
		List<NoticeVO> vo = sql.selectList(null);
		
		return "";
	}
}
