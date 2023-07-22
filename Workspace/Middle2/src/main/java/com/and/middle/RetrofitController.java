package com.and.middle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import retrofit.CrudRetrofitVO;
import retrofit.CurdRetrofitDAO;

@RestController @RequestMapping("/retrofit")
public class RetrofitController {
	@Autowired CurdRetrofitDAO dao;
	
	
	@RequestMapping(value = "/list" , produces = "text/html;charset=utf-8")
	public String list() {
		return new Gson().toJson(dao.select());
	}
	@RequestMapping("/insert")
	public String insert(CrudRetrofitVO vo) {
		return new Gson().toJson(dao.insert(vo));
	}
	@RequestMapping("/update")
	public String update(CrudRetrofitVO vo) {
		return new Gson().toJson(dao.update(vo));
	}
	@RequestMapping("/delete")
	public String delete(CrudRetrofitVO vo) {
		return new Gson().toJson(dao.delete(vo));
	}
}
