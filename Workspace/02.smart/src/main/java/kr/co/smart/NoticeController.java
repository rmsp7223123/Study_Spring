package kr.co.smart;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import smart.member.MemberDAO;
import smart.member.MemberVO;
import smart.notice.NoticeDAO;
import smart.notice.NoticeVO;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	@Autowired
	private NoticeDAO service;

	// 신규 공지글 등록 처리 요청
	@RequestMapping("/register")
	public String register(NoticeVO vo) {
		// 화면에서 입력한 정보로 DB에 신규저장
		service.notice_regist(vo);
		return "redirect:list";
	}

	// 신규 공지글 등록 화면 요청
	@RequestMapping("/new")
	public String regist() {
		return "notice/new";
	}

	@Autowired
	private MemberDAO member;
	@Autowired
	private BCryptPasswordEncoder pe;

	// 공지글 목록 화면 요청
	@RequestMapping("/list")
	public String list(Model model, HttpSession session) {
//		임시 로그인 처리
		String userid = "admin";
		String userpw = "admin";
		MemberVO login = member.member_info(userid);
		if (pe.matches(userpw, login.getUserpw())) {
			session.setAttribute("loginInfo", login);
		}

//		DB에서 공지글 목록을 조회해 목록화면에 출력 할 수 있도록 Model에 담는다
		session.setAttribute("category", "no");

//		List<NoticeVO> list = service.notice_list();
//		model.addAttribute("list", list);
		model.addAttribute("list", service.notice_list());

		return "notice/list";
	}
}
