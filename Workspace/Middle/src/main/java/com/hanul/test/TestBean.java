package com.hanul.test;

import org.springframework.stereotype.Component;

@Component(value = "bean")
public class TestBean {
	public TestBean() {
		System.out.println("Autowired");
	}
}
