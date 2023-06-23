package kr.co.smart;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notice")
public class NoticeController {

	@RequestMapping("/list")
	public String list(Model model, HttpSession session) {
//		DB에서 공지글 목록을 조회해 목록화면에 출력 할 수 있도록 Model에 담는다
		session.setAttribute("category", "no");

		return "notice/list";
	}
}
