package smart.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public class CommonUtility {
//	context root url 지정
	public String appURL(HttpServletRequest req) {
//		http://localhost/smart
		StringBuffer url = new StringBuffer("http://");
		url.append(req.getServerName()); // 192.168.0.87
		url.append(req.getContextPath()); // /smart
		return url.toString();
	}

//	이메일 보내기 처리
	public boolean sendPassword() {
		boolean send = true;
		return send;
	}
}
