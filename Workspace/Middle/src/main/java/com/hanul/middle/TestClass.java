package com.hanul.middle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class TestClass {
	@Autowired
	@Qualifier("tt")
	TestVO vo;
}
