package smart.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;

import smart.member.MemberVO;

@Service
public class CommonUtility {

	private void emailServerConnect(HtmlEmail email) {
		email.setHostName("smtp.naver.com"); // 메일서버지정
		email.setAuthentication("아이디", "비밀번호"); // 아이디 비밀번호 입력
		email.setSSLOnConnect(true); // 로그인
	}

	private String EMAIL_ADDRESS = "mbj98@naver.com";

//	context root url 지정
	public String appURL(HttpServletRequest req) {
//		http://localhost/smart
		StringBuffer url = new StringBuffer("http://");
		url.append(req.getServerName()); // 192.168.0.87
		url.append(req.getContextPath()); // /smart
		return url.toString();
	}

//	이메일 보내기 처리
	public boolean sendPassword(MemberVO vo, String pw) {
		boolean send = true;
		HtmlEmail email = new HtmlEmail();
		email.setCharset("utf-8");
		email.setDebug(true);

		emailServerConnect(email);

		try {
			email.setFrom(EMAIL_ADDRESS, "관리자");// 보내는사람 : 관리자
			email.addTo(vo.getEmail(), vo.getName());// 받는사람

			email.setSubject("임시비밀번호 생성");// 제목

			StringBuffer msg = new StringBuffer();

			msg.append("<h3>[").append(vo.getName()).append("]님 임시 비밀번호가 발급되었습니다.</h3>");
			msg.append("<div>아이디 : ").append(vo.getUserid()).append("</div>");
			msg.append("<div>임시 비밀번호 :<strong> ").append(pw).append("</strong></div>");
			msg.append("<div>발급된 임시 비밀번호로 로그인 한 후 비밀번호를 변경해주세요.</div>");
			email.setHtmlMsg(msg.toString()); // 내용쓰기
			email.send(); // 전송
		} catch (Exception e) {
			send = false;
		}
		return send;
	}
}
