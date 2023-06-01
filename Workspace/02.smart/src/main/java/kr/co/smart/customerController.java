package kr.co.smart;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import customer.CustomerServiceImpl;

@Controller
public class customerController {

	@Autowired
	private CustomerServiceImpl service;

	@RequestMapping("/list.cu")
	public String list(HttpSession session) {
		session.setAttribute("category", "cu");

		service.customer_list();
		return "customer/list";
	}
}
