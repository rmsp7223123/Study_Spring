package kr.co.smart;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import smart.hr.EmployeeVO;
import smart.hr.HrDAO;

@Controller
@RequestMapping("/hr")
public class HrController {
	@Autowired
	private HrDAO service;

//	사원목록화면 요청
	@RequestMapping("/list")
	public String list(HttpSession session, Model model, @RequestParam(defaultValue = "-1") int department_id) {
		session.setAttribute("category", "hr");

//		회사의 사원이 소속된 부서목록 조회
		model.addAttribute("department_id", department_id);
		model.addAttribute("departments", service.employee_department_list());

//		사원목록 조회
		model.addAttribute("list",
				department_id == -1 ? service.employee_list() : service.employee_list(department_id));
		return "hr/list";
	}

	@RequestMapping("/info")
	public String info(int id, Model model) {
		model.addAttribute("vo", service.employee_info(id));
		return "hr/info";
	}

//	사원정보 수정화면 요청
	@RequestMapping("/modify")
	public String modify(int id, Model model) {
		model.addAttribute("vo", service.employee_info(id));
		model.addAttribute("departments", service.department_list());
		model.addAttribute("jobs", service.job_List());
		return "hr/modify";
	}

//	사원정보 수정저장처리 요청
	@RequestMapping("/update")
	public String update(EmployeeVO vo) {
		service.employee_update(vo);
		return "redirect:info?id=" + vo.getEmployee_id();
	}

//	사원정보 삭제처리 요청
	@RequestMapping("/delete")
	public String delete(int id) {
		service.employee_delete(id);
		return "redirect:list";
	}

	@RequestMapping("/new")
	public String insert(Model model) {
		model.addAttribute("departments", service.department_list());
		model.addAttribute("jobs", service.job_List());
		return "hr/new";
	}

//	사원정보 추가
	@RequestMapping("/insert")
	public String insert1(Model model, EmployeeVO vo) {
		model.addAttribute("vo", service.employee_insert(vo));
		return "redirect:list";
	}
}
