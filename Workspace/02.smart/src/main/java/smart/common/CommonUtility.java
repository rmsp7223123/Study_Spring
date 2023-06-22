package smart.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import smart.member.MemberVO;

@Service
public class CommonUtility {

	// 파일업로드
	public String fileUpload(String category, MultipartFile file, HttpServletRequest req) {
		// D:\Study_Spring\Workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\02.smart\resources
		String path = req.getSession().getServletContext().getRealPath("resources");
		String upload = "/upload/" + category + new SimpleDateFormat("yyyy/MM/dd").format(new Date());
		path += upload;
		// 파일을 저장해 둘 폴더가 없는경우 폴더 만들기
		File folder = new File(path);
		if (!folder.exists())
			folder.mkdirs();
		String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		try {
			file.transferTo(new File(path, filename));
		} catch (Exception e) {
		}
		return appURL(req) + upload + "/" + filename;
	}

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

//	API 요청
	public String requestAPI(String apiURL) {
		String response = "";
		try {
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			BufferedReader br;
			System.out.print("responseCode=" + responseCode);
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"));
			}
			String inputLine;
			StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			br.close();
			response = res.toString();
			if (responseCode != 200) {
				System.out.println(res.toString());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return response;
	}

	public String requestAPI(String apiURL, String property) {
		String response = "";
		try {
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", property);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			System.out.print("responseCode=" + responseCode);
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"));
			}
			String inputLine;
			StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			br.close();
			response = res.toString();
			if (responseCode != 200) {
				System.out.println(res.toString());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return response;
	}
}
