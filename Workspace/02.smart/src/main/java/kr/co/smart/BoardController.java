package kr.co.smart;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import smart.board.BoardDAO;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardDAO service;

	@RequestMapping("new")
	public String board() {
		return "board/new";
	}

	@RequestMapping("/list")
	public String list(HttpSession session) {
		session.setAttribute("category", "bo");

		return "board/list";
	}
}
