package kr.co.smart;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import smart.common.CommonUtility;
import smart.member.MemberDAO;
import smart.member.MemberVO;

@Controller
@RequestMapping("member")
public class MemberController {
	@Autowired
	private BCryptPasswordEncoder pe;
	@Autowired
	private MemberDAO service;

//	로그인 처리 요청
	@RequestMapping(value = "/smartLogin")
	public String login1(String userid, String userpw, HttpSession session, HttpServletRequest req,
			RedirectAttributes rd) {
		MemberVO vo = service.member_info(userid);
		boolean match = false;
		if (vo != null) {
			match = pe.matches(userpw, vo.getUserpw());
		}
		if (match) {
			return "redirect:/";
		} else {
			rd.addFlashAttribute("fail", true);
			return "redirect:login";
		}
	}

	@RequestMapping("/login")
	public String login() {

		return "default/member/login";
	}

	/*
	 * @RequestMapping("/login") public String login() {
	 * 
	 * return "default/member/login"; }
	 * 
	 * @RequestMapping(value = "/smartLogin", produces = "text/html; charset=utf-8")
	 * 
	 * @ResponseBody public String login1(String userid, String userpw, HttpSession
	 * session, HttpServletRequest req) { // 화면에서 입력한 아이디, 비밀번호가 일치하는 회원정보가 있는지 DB에서
	 * 확인 // 입력한 아이디에 해당하는 회원정보 조회 MemberVO vo = service.member_info(userid);
	 * boolean match = false; if (vo != null) { // 아이디가 일치하는 회원정보가 있고 // 로그인이 되는 경우
	 * match = pe.matches(userpw, vo.getUserpw()); // 비밀번호 확인 }
	 * 
	 * StringBuffer msg = new StringBuffer("<script>");
	 * 
	 * if (match) { session.setAttribute("loginInfo", vo); // 세션에 로그인한 회원정보 담기
	 * msg.append("location='").append(common.appURL(req)).append("'"); } else { //
	 * 로그인이 되지 않는 경우 msg.append("alert('아이디나 비밀번호가 일치하지 않습니다.'); history.go(-1);");
	 * 
	 * } msg.append("</script>"); return msg.toString(); }
	 */
	@Autowired
	private CommonUtility common;

//	비밀번호 찾기 화면 요청
	@RequestMapping("/findPassword")
	public String find() {
		return "default/member/find";
	}

//	비밀번호 찾기 처리 요청
	@RequestMapping(value = "/resetPassword", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String reset(MemberVO vo) {
//		화면에서 입력한 아이디/이메일이 일치하는게 있으면 임시 비밀번호 발급
//		임시 비밀번호를 생성한 후 DB에 저장 ==> 임시 비밀번호를 이메일로 보내줌
		String name = service.member_userid_email(vo);
		StringBuffer msg = new StringBuffer("<script>");
		if (name == null) {
			msg.append("alert('아이디나 이메일이 맞지 않습니다.');");
			msg.append("location='findPassword'");
		} else {
			vo.setName(name);
			String pw = UUID.randomUUID().toString(); // adfasdfdf_123123safsdf-asd1
			pw = pw.substring(pw.lastIndexOf("-") + 1); // asd1
			vo.setUserpw(pe.encode(pw));
			if (service.member_resetPassword(vo) == 1 && common.sendPassword()) {

			} else {

			}
		}
		msg.append("</script>");
		return msg.toString();

	}
}
