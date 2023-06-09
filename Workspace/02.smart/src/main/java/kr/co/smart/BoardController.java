package kr.co.smart;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import smart.board.BoardCommentVO;
import smart.board.BoardDAO;
import smart.board.BoardVO;
import smart.board.FileVO;
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

	// 댓글 정보 삭제 처리
	@RequestMapping("/comment/delete")
	@ResponseBody
	public boolean comment_delete(int id) {
		// 해당 댓글 정보를 db에서 삭제
		return service.board_comment_delete(id) == 1 ? true : false;
	}

	// 댓글 정보 수정 처리
//	@RequestMapping(value = "/comment/update", produces = "text/html; charset=utf-8")
//	@ResponseBody
	// json으로 보내진 정보를 담기 위한 annotation : @RequestBody
	@ResponseBody
	@RequestMapping(value = "/comment/update")
	public HashMap<String, Object> comment_update(@RequestBody BoardCommentVO vo) {
		// 화면에서 변경 입력한 정보를 DB에 저장 처리
//		return service.board_comment_update(vo) == 1 ? "성공" : "실패";
		// 응답 화면에서 댓글 목록 전체를 다시 조회 해 오지 않고 변경 저장 된 댓글만 반영되게 처리
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (service.board_comment_update(vo) == 1) {
			map.put("message", "성공");
			map.put("content", vo.getContent());
		} else {
			map.put("message", "실패");
		}
		return map;
	}

	// 댓글 목록 조회
	@RequestMapping("/comment/list/{board_id}")
	public String comment_list(@PathVariable int board_id, Model model) {
		// 해당 방명록 글에 대한 댓글목록을 DB에서 조회, 댓글 목록 화면에 출력
		model.addAttribute("list", service.board_comment_list(board_id));
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		return "board/comment/comment_list";
	}

	// 댓글 등록 처리
	@RequestMapping("/comment/register")
	@ResponseBody
	public boolean comment_register(BoardCommentVO vo) {
		// 화면에서 입력한 댓글 정보를 DB에 신규 저장
		return service.board_comment_register(vo) == 1 ? true : false;
	}

	// 선택한 방명록 정보 수정처리 요청
	@RequestMapping("/update")
	public String update(BoardVO vo, PageVO page, Model model, MultipartFile file[], HttpServletRequest req,
			String removed) {
		// 첨부된 파일들 담기
		vo.setFileList(common.attachedFiles("board", file, req));
		// 화면에 업데이트 요청
		if (service.board_update(vo) > 0) {
			// 삭제 대상 파일이 있는 경우는 삭제처리 : DB+물리적 파일
			if (!removed.isEmpty()) {
				// DB에서 삭제하기 전에 삭제 할 파일 정보 조회해두기
				List<FileVO> files = service.board_file_removed(removed);
				// DB 삭제
				for (FileVO f : files) {
					if (service.board_file_delete(f.getId()) > 0) {
						// 물리적 파일 삭제
						common.deletedFile(f.getFilepath(), req);
					}
				}
			}
		}
		model.addAttribute("url", "board/info");
		model.addAttribute("page", page);
		model.addAttribute("id", vo.getId());
		// 화면에서 변경 입력 정보로 DB에 변경 저장
		return "board/redirect";
	}

	// 방명록의 글 수정 요청
	@RequestMapping("/modify")
	public String modify(Model model, int id, PageVO page) {
		model.addAttribute("vo", service.board_info(id));
		model.addAttribute("page", page);
		return "board/modify";
	}

	// 방명록의 글 삭제 요청
	@RequestMapping("/delete")
	public String delete(int id, PageVO page, HttpServletRequest req, Model model) {
		BoardVO vo = service.board_info(id);
		List<FileVO> list = vo.getFileList();
		if (service.board_delete(id) > 0) {
			for (FileVO file : list) {
				common.deletedFile(file.getFilepath(), req);
			}
		}

		service.board_delete(id);
		model.addAttribute("url", "board/list");
		model.addAttribute("page", page);
		return "board/redirect";
	}

	// 방명록의 선택한 글 첨부파일 다운로드 요청
	@RequestMapping("/download")
	public void download(int file, HttpServletRequest req, HttpServletResponse res) {
		// 해당 파일정보를 조회해 서버의 물리적 파일정보를 읽어와 클라이언트에 다운로드 처리
		FileVO vo = service.board_file_info(file);
		common.fileDownload(vo.getFilename(), vo.getFilepath(), req, res);
	}

	// 방명록 정보 화면 요청
	@RequestMapping("/info")
	public String info(Model model, int id, PageVO page) {
		// 조회수 증가 처리
		service.board_read(id);

		// 선택한 방명록 글 정보를 DB에서 조회해와 화면에 출력할 수 있도록 Model에 담기
		model.addAttribute("vo", service.board_info(id));
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		model.addAttribute("page", page);
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
