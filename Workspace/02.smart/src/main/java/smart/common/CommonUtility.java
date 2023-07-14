package smart.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import smart.board.FileVO;
import smart.member.MemberVO;

@Service
public class CommonUtility {

	// 첨부파일 여러개를 업로드 하는 처리
	public ArrayList<FileVO> attachedFiles(String category, MultipartFile[] files, HttpServletRequest req) {
		ArrayList<FileVO> list = null;
		for (MultipartFile attached : files) {
			if (attached.isEmpty())
				continue;
			if (list == null)
				list = new ArrayList<FileVO>();
			FileVO filevo = new FileVO();
			filevo.setFilename(attached.getOriginalFilename());
			filevo.setFilepath(fileUpload(category, attached, req));
			list.add(filevo);
		}
		return list;
	}

	// 첨부파일 삭제 : 디스크에 저장된 물리적 파일 삭제
	public void deletedFile(String filepath, HttpServletRequest req) {
		if (filepath != null) {
			filepath = filepath.replace(appURL(req), "d://Spring_app/" + req.getContextPath());
			File file = new File(filepath);
			if (file.exists())
				file.delete();
		}
	}

	// 파일 다운로드
	public void fileDownload(String filename, String filepath, HttpServletRequest req, HttpServletResponse res) {
//		filepath = http://192.168.0.87/smart/upload/profile/2023/06/23/abc.pmg
//		appURL = http://192.168.0.87/smart
		filepath = filepath.replace(appURL(req), "d://Spring_app/" + req.getContextPath());

//		다운로드 할 파일객체 생성
		File file = new File(filepath);
		String mime = req.getSession().getServletContext().getMimeType(filepath);
		res.setContentType(mime);
//		파일 IO : 읽기/쓰기 = 단위 문자 : reader/writer, 단위 byte : input/output 
//		파일을 첨부해서 쓰기작업하기
		try {
			filename = URLEncoder.encode(filename, "utf-8").replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		res.setHeader("content-disposition", "attachment; filename =" + filename);
		try {
			FileCopyUtils.copy(new FileInputStream(file), res.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 파일업로드
	public String fileUpload(String category, MultipartFile file, HttpServletRequest req) {
		// D:\Study_Spring\Workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\02.smart\resources

//		D:\Spring_app\smart
//		String path = req.getSession().getServletContext().getRealPath("resources");
		String path = "D:\\Spring_app" + req.getContextPath();
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
//		http://192.168.0.87/smart/upload/profile/2023/06/23/abc.pmg
		return appURL(req) + upload + "/" + filename;
	}

	private void emailServerConnect(HtmlEmail email) {
		email.setHostName("smtp.naver.com"); // 메일서버지정
		email.setAuthentication("아이디", "비밀번호"); // 아이디 비밀번호 입력
		email.setSSLOnConnect(true); // 로그인
	}

	private String EMAIL_ADDRESS = "rmsp7223123@naver.com";

//	context root url 지정
	public String appURL(HttpServletRequest req) {
//		http://localhost/smart
		StringBuffer url = new StringBuffer("http://");
		url.append(req.getServerName()); // 192.168.0.87
		url.append(req.getContextPath()); // /smart
		return url.toString();
	}

//	이메일 보내기 : 회원가입 축하 메시지 전송
	public void sendWelcome(MemberVO vo, String welcomeFile) {
		HtmlEmail email = new HtmlEmail();
		email.setCharset("utf-8");
		email.setDebug(true);
//		이메일 서버 지정
		emailServerConnect(email);
		try {
			email.setFrom(EMAIL_ADDRESS, "스마트웹&앱 관리자");
			email.addTo(vo.getEmail(), vo.getName());
			email.setSubject("회원가입 축하메시지 확인");
			StringBuffer content = new StringBuffer();

			content.append("<body>");
			content.append(
					"<h3><a target='_blank' href='https://t1.daumcdn.net/cfile/tistory/270F6B3A567D7EA706'>한울 스마트 웹&앱 과정</a></h3>");
			content.append("<div>가입을 축하합니다.</div>");
			content.append("<div>첨부된 파일을 확인해주세요.</div>");
			content.append("</body>");
			email.setHtmlMsg(content.toString());
			EmailAttachment file = new EmailAttachment();
			file.setPath(welcomeFile); // 파일 선택
			email.attach(file); // 파일 첨부
			email.send();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

//	이메일 보내기 처리 : 임시비밀번호 전송
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

//  공공 데이터 API 요청 결과 문자열을 json으로 변환 후, 필요한 데이터만 수집하는 메소드
	public Map<String, Object> requestAPIInfo(String apiURL) {
		try {
			JSONObject json = new JSONObject(requestAPI(apiURL));
			json = json.getJSONObject("response");

			if (json.has("body")) {
				return json.getJSONObject("body").toMap();
			} else {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("items", null);
				map.put("totalCount", 0);
				map.put("pageNo", 1);
				return map;
			}
		} catch (Exception e) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("items", null);
			map.put("totalCount", 0);
			map.put("pageNo", 1);
			return map;
		}
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
