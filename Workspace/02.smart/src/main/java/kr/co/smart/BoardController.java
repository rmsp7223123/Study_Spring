package kr.co.smart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import smart.board.BoardDAO;
import smart.board.BoardVO;
import smart.common.CommonUtility;
import smart.common.PageVO;
import smart.member.MemberDAO;
import smart.member.MemberVO;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private CommonUtility common;

	@Autowired
	private BoardDAO service;

	@Autowired
	private MemberDAO member;
	@Autowired
	private BCryptPasswordEncoder pe;

	@RequestMapping("new")
	public String board() {
		return "board/new";
	}

	// 방명록 정보 화면 요청
	@RequestMapping("/info")
	public String info(Model model, int id, PageVO page) {
		// 선택한 방명록 글 정보를 DB에서 조회해와 화면에 출력할 수 있도록 Model에 담기
		model.addAttribute("vo", service.board_info(id));
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		return "board/info";
	}

	// 방명록 신규 저장처리 요청
	@RequestMapping("/register")
	public String register(BoardVO vo, MultipartFile file[], HttpServletRequest req) {
//		화면에서 입력한 정보를 DB에 신규 저장 후 응답화면 연결 - 목록화면
//		첨부된 파일 목록을 BoardVO에 담기
		vo.setFileList(common.attachedFiles("board", file, req));
		service.board_register(vo);
		return "redirect:list";
	}

	// 방명록 목록 화면 요청
	@RequestMapping("/list")
	public String list(HttpSession session, PageVO page, Model model) {

//		String userid = "ansqudwns9";
//		String userpw = "123qQ";
//		MemberVO login = member.member_info(userid);
//		if (pe.matches(userpw, login.getUserpw())) {
//			session.setAttribute("loginInfo", login);
//		}

		model.addAttribute("page", service.board_list(page));
		session.setAttribute("category", "bo");
		return "board/list";
	}
}
