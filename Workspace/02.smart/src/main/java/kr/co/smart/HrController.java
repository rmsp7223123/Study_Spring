package kr.co.smart;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import smart.hr.HrDAO;

@Controller
@RequestMapping("/hr")
public class HrController {
	@Autowired
	private HrDAO service;

//	사원목록화면 요청
	@RequestMapping("/list")
	public String list(HttpSession session, Model model) {
		session.setAttribute("category", "hr");
		model.addAttribute("list", service.employee_list());
		return "hr/list";
	}

}
