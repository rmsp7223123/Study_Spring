package kr.co.smart;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

<<<<<<< HEAD
import smart.customer.CustomerServiceImpl;
import smart.customer.CustomerVO;
=======
import customer.CustomerServiceImpl;
import customer.CustomerVO;
>>>>>>> parent of a41c903 (99)

@Controller
public class customerController {

	@Autowired
	private CustomerServiceImpl service;

	@RequestMapping("/list.cu")
<<<<<<< HEAD
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
=======
	public String list(HttpSession session, Model model) {
		session.setAttribute("category", "cu");

		List<CustomerVO> list = service.customer_list();
		model.addAttribute("list", list);
>>>>>>> parent of a41c903 (99)
		return "customer/list";
	}
}
