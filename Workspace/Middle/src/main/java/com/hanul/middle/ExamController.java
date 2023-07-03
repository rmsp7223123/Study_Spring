package com.hanul.middle;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExamController {
//	Request 요청 : 요청에 대한 모든 정보를 가지고 있는 객체(Tomcat)
//	Model : 다음 페이지에서 데이터가 필요하다면 넣어주는 처리
//			Controller(Model) => jsp (Model, el);

	@RequestMapping("/ex1")
	public void ex1(HttpServletRequest req, String board_no, HttpServletResponse res) throws IOException {
//		System.out.println("확인ㅇ1  " + req.getParameter("board_no"));
//		System.out.println(board_no);
		res.getWriter().println("asdasdasdsadsdaasd");
	}
}
