package kr.co.smart;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

<<<<<<< HEAD
import customer.CustomerServiceImpl;
import customer.CustomerVO;
=======
import smart.customer.CustomerServiceImpl;
import smart.customer.CustomerVO;
>>>>>>> 9b815261d53cc4f2093b795c56c82240c9f7e960

@Controller
public class customerController {

	@Autowired
	private CustomerServiceImpl service;

	/*
	 * public CustomerController( CustomerServiceImpl service) { this.service =
	 * service; }
	 */

	// 고객정보 삭제처리 요청
	@RequestMapping("/delete.cu")
	public String delete(int id) {
		// 비지니스로직: 해당 고객정보를 DB에서 삭제한 후
		service.customer_delete(id);
		// 프리젠테이션로직: 고객목록화면으로 응답
		return "redirect:list.cu";
	}

	// 고객정보 변경저장 처리 요청
	@RequestMapping("/update.cu")
	public String update(CustomerVO vo) {
		// 비지니스로직: 화면에서 변경입력한 정보로 DB에 변경저장한 후
		service.customer_update(vo);

		// 프리젠테이션로직: 응답화면연결 - 정보화면
		return "redirect:info.cu?id=" + vo.getId();
	}

	// 고객정보수정화면 요청
	@RequestMapping("/modify.cu")
	public String modify(@RequestParam Integer id, Model model) {
		// 비지니스로직: 선택한 고객정보를 DB에서 조회해와
		// 수정화면에 출력할 수 있도록 데이터를 Model객체에 담는다
		model.addAttribute("vo", service.customer_info(id));
		// 프리젠테이션로직: 응답화면연결 - 수정화면
		return "customer/modify";
	}

	// 고객상세정보화면 요청
	@RequestMapping("/info.cu")
	public String info(Model model, int id) {
		// 비지니스로직: 선택한 고객정보를 DB에서 조회해와
		// 정보화면에 출력할 수 있도록 데이터를 Model객체에 담는다
		CustomerVO vo = service.customer_info(id);
		model.addAttribute("vo", vo);
		// 응답화면연결
		return "customer/info";
	}

	// DML(Insert/Update/Delete) 처리가 되는 요청에 대해서는
	// 반드시 redirect 로 화면응답
	// 신규고객정보 저장처리 요청
	@RequestMapping("/register.cu")
	public String register(CustomerVO vo) {
		// 비지니스로직: 화면에서 입력한 정보를 DB에 신규저장한 후
		service.customer_insert(vo);
		// 프리젠테이션로직(응답화면연결): 목록화면
		return "redirect:list.cu";
	}

	// 신규고객정보입력화면 요청
	@RequestMapping("/new.cu")
	public String customer() {
		return "customer/new";
	}

	// 고객목록화면 요청
	@RequestMapping("/list.cu")
<<<<<<< HEAD
	public String list(HttpSession session, Model model) {
		session.setAttribute("category", "cu");

		List<CustomerVO> list = service.customer_list();
		model.addAttribute("list", list);
=======
	public String list(HttpSession session, Model model, String name) {
		// null, "", "홍"
		session.setAttribute("category", "cu");

		// 비지니스로직 - DB에서 고객목록을 조회한다
		/*
		 * List<CustomerVO> list = null; if( name == null ) { list =
		 * service.customer_list(); }else { list = service.customer_list(name); }
		 */

		List<CustomerVO> list = name == null ? service.customer_list() : service.customer_list(name);

		model.addAttribute("list", list);
		model.addAttribute("name", name);

		// 응답화면연결
>>>>>>> 9b815261d53cc4f2093b795c56c82240c9f7e960
		return "customer/list";
	}

}
