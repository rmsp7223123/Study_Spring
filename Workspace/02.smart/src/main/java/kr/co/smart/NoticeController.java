package kr.co.smart;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import smart.common.CommonUtility;
import smart.common.PageVO;
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

	// 답글쓰기 요청
	@RequestMapping("/reply")
	public String reply() {
		return "notice/reply";
	}

	// 공지글 정보 수정 후 저장처리
	@RequestMapping("/update")
	public String update(NoticeVO vo, MultipartFile file, HttpServletRequest req, PageVO page)
			throws UnsupportedEncodingException {
		// 원래 첨부되어 있던 파일정보 조회
		NoticeVO before = service.notice_info(vo.getId());

		if (file.isEmpty()) {
			// 첨부파일이 없는경우 : 원래 있다가 삭제, 원래 있던없던 그대로 사용하는 경우
			// 원래 있었으나 그대로 두는 경우 => 파일명을 이전정보로 담는다.
			if (!vo.getFilename().isEmpty()) {
				vo.setFilename(before.getFilename());
				vo.setFilepath(before.getFilepath());
			}
		} else {
			// 첨부파일이 있는경우
			// 원래 있던없던 첨부파일 업로드
			vo.setFilename(file.getOriginalFilename());
			vo.setFilepath(common.fileUpload("notice", file, req));
		}

		// 화면에서 변경입력한 정보로 DB에 변경저장 한 후 응답화면 연결
		if (service.notice_update(vo) > 0) {
			// 물리적 파일 삭제 처리
			if (file.isEmpty()) {
				// 원래 있으나 화면에서 삭제한 경우
				if (vo.getFilename().isEmpty()) {
					common.deletedFile(before.getFilepath(), req);
				}
			} else {
				// 원래 있고 바꿔서 첨부한 경우
				common.deletedFile(before.getFilepath(), req);

			}
		}
		return "redirect:info?id=" + vo.getId() + "&curPage=" + page.getCurPage() + "&search=" + page.getSearch()
				+ "&keyword=" + URLEncoder.encode(page.getKeyword(), "utf-8");
	}

	// 공지글정보 수정 요청
	@RequestMapping("/modify")
	public String modify(int id, Model model, PageVO page) {
//		해당글의 정보를 DB에서 조회해 수정화면에 출력할 수 있도록 model에 담음
		model.addAttribute("vo", service.notice_info(id));
		model.addAttribute("page", page);
		return "notice/modify";
	}

	// 공지글정보 삭제 요청
	@RequestMapping("/delete")
	public String delete(int id, HttpServletRequest req, PageVO page) throws UnsupportedEncodingException {

		// 첨부파일이 있는 경우 물리적인 파일을 찾아 삭제 할 수 있도록 파일 정보를 조회
		NoticeVO vo = service.notice_info(id);
		if (service.notice_delete(id) > 0) {
			common.deletedFile(vo.getFilepath(), req);
		}
		service.notice_delete(id);
		return "redirect:list?" + "curPage=" + page.getCurPage() + "&search=" + page.getSearch() + "&keyword="
				+ URLEncoder.encode(page.getKeyword(), "utf-8");
	}

	// 공지글 첨부파일 다운로드처리 요청
	@RequestMapping("/download")
	public void download(int id, HttpServletRequest req, HttpServletResponse res) {
		// 해당 글의 첨부파일 정보를 조회 후 서버로부터 파일 정보를 찾고 다운로드
		NoticeVO vo = service.notice_info(id);
		common.fileDownload(vo.getFilename(), vo.getFilepath(), req, res);
	}

	// 공지글 정보 화면 요청
	@RequestMapping("/info")
	public String info(int id, Model model, PageVO page) {
		// 조회수 증가처리
		service.notice_read(id);

//		선택한 공지글 정보를 DB에서 조회해 화면에서 출력할 수 있도록 Model에 담음
		model.addAttribute("crlf", "\r\n"); // Carriage Return Line feed
		model.addAttribute("lf", "\n"); // Line feed
		model.addAttribute("vo", service.notice_info(id));
		model.addAttribute("page", page);
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
	public String list(Model model, HttpSession session, PageVO page) {
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
		model.addAttribute("page", service.notice_list(page));

		return "notice/list";
	}
}
