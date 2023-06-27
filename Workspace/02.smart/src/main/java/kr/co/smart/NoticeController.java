package kr.co.smart;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import smart.common.CommonUtility;
import smart.member.MemberDAO;
import smart.member.MemberVO;
import smart.notice.NoticeDAO;
import smart.notice.NoticeVO;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	@Autowired
	private NoticeDAO service;
	@Autowired
	private CommonUtility common;

	// 공지글 첨부파일 다운로드처리 요청
	@RequestMapping("/download")
	public void download(int id, HttpServletRequest req, HttpServletResponse res) {
		// 해당 글의 첨부파일 정보를 조회 후 서버로부터 파일 정보를 찾고 다운로드
		NoticeVO vo = service.notice_info(id);
		common.fileDownload(vo.getFilename(), vo.getFilepath(), req, res);
	}

	// 공지글 정보 화면 요청
	@RequestMapping("/info")
	public String info(int id, Model model) {
		// 조회수 증가처리
		service.notice_read(id);

//		선택한 공지글 정보를 DB에서 조회해 화면에서 출력할 수 있도록 Model에 담음
		model.addAttribute("crlf", "\r\n"); // Carriage Return Line feed
		model.addAttribute("lf", "\n"); // Line feed
		model.addAttribute("vo", service.notice_info(id));
		return "notice/info";
	}

	// 신규 공지글 등록 처리 요청
	@RequestMapping("/register")
	public String register(NoticeVO vo, MultipartFile file, HttpServletRequest req) {
		// 첨부한 파일이 있는 경우
		if (!file.isEmpty()) {
			vo.setFilename(file.getOriginalFilename());
			vo.setFilepath(common.fileUpload("notice", file, req));
		}

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
