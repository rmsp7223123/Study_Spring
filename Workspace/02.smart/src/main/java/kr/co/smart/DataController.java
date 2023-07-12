package kr.co.smart;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import smart.common.CommonUtility;

@Controller
@RequestMapping("/data")
public class DataController {

	private String key = "3pIdezEksFzyrjO65AKqJ1aKmjIY8DZcjzblwI9DqnJa5kpHB97MB6bbnz4Vnp8roIw2j7kjORYibrgQH3j1ZA%3D%3D";

	@Autowired
	private CommonUtility common;

	// 공공 데이터 목록 화면
	@RequestMapping("/list")
	public String list(HttpSession session) {
		session.setAttribute("category", "da");
		return "data/list";
	}

	// 약국 목록 조회
//	@RequestMapping(value = "/pharmacy", produces = "text/html; charset=utf-8")
	@ResponseBody
	@RequestMapping(value = "/pharmacy")
	public HashMap<String, Object> pharmacy(int pageNo, int rows) {
		StringBuffer url = new StringBuffer("http://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList");
		url.append("?ServiceKey=").append(key);
		url.append("&_type=json");
		url.append("&pageNo=").append(pageNo);
		url.append("&numOfRows=").append(rows);
		HashMap<String, Object> map = new Gson().fromJson(common.requestAPI(url.toString()),
				new TypeToken<HashMap<String, Object>>() {
				}.getType());
		return map;
	}

	@RequestMapping("/animal/list")
	public void animal_list() {
			
	}
}
