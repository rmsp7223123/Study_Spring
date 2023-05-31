package kr.co.smart;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class customerController {
	@RequestMapping("/list.cu")
	public String list() {
		return "customer/list";
	}
}
