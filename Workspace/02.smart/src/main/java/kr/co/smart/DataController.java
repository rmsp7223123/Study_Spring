package kr.co.smart;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public HashMap<String, Object> pharmacy(int pageNo, int rows, String name) throws UnsupportedEncodingException {
		StringBuffer url = new StringBuffer("http://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList");
		url.append("?ServiceKey=").append(key);
		url.append("&_type=json");
		url.append("&pageNo=").append(pageNo);
		if (!name.isEmpty()) {
			url.append("&yadmNm=").append(URLEncoder.encode(name, "utf-8"));
		}
		url.append("&numOfRows=").append(rows);
		HashMap<String, Object> map = new Gson().fromJson(common.requestAPI(url.toString()),
				new TypeToken<HashMap<String, Object>>() {
				}.getType());
		return map;
	}

//	@RequestMapping("/animal/list")
//	@ResponseBody
//	public HashMap<String, Object> animal_list(int pageNo, int rows) {
//		StringBuffer url = new StringBuffer("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/");
//		url.append("abandonmentPublic?serviceKey=").append(key);
//		url.append("&_type=json");
//		url.append("&pageNo=").append(pageNo);
//		url.append("&numOfRows=").append(rows);
//		return new Gson().fromJson(common.requestAPI(url.toString()), new TypeToken<HashMap<String, Object>>() {
//		}.getType());
//	}

//	@RequestMapping("/animal/list")
//	public String animal_list(int pageNo, int rows, Model model) {
//		StringBuffer url = new StringBuffer("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/");
//		url.append("abandonmentPublic?serviceKey=").append(key);
//		url.append("&_type=json");
//		url.append("&pageNo=").append(pageNo);
//		url.append("&numOfRows=").append(rows);
//		model.addAttribute("list", common.requestAPIInfo(url.toString()));
//		return "data/animal/animal_list";
//	}
	
	// jsp에서 보낸 json 파라미터는 바로 데이터 객체에 담기지 않음 ==> @RequestBody로 처리
	@RequestMapping("/animal/list")
	public String animal_list(@RequestBody HashMap<String, Object> map, Model model) {
		StringBuffer url = new StringBuffer("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/");
		url.append("abandonmentPublic?serviceKey=").append(key);
		url.append("&_type=json");
		url.append("&pageNo=").append(map.get("curPage"));
		url.append("&numOfRows=").append(map.get("pageList"));
		url.append("&upr_cd=").append(map.get("sido"));
		model.addAttribute("list", common.requestAPIInfo(url.toString()));
		return "data/animal/animal_list";
	}
	
	// 시도 정보 요청
	@RequestMapping("/animal/sido")
	public String animal_sido(Model model) {
		StringBuffer url = new StringBuffer("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/");
		url.append("sido?serviceKey=").append(key);
		url.append("&_type=json");
		url.append("&numOfRows=30");
		model.addAttribute("list",common.requestAPIInfo(url.toString()));
		return "data/animal/animal_sido";
	}
	
	//시군구 정보 요청
	@RequestMapping("/animal/sigungu")
	public String animal_sigungu(Model model, String sido) {
		StringBuffer url = new StringBuffer("http://apis.data.go.kr/1543061/abandonmentPublicSrvc/");
		url.append("sigungu?serviceKey=").append(key);
		url.append("&_type=json");
		url.append("&upr_cd=").append(sido);
		model.addAttribute("list",common.requestAPIInfo(url.toString()));
		return "data/animal/animal_sigungu";
	}
	
}
