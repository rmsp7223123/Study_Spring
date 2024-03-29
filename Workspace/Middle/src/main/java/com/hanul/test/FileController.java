package com.hanul.test;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.google.gson.Gson;

@RestController
public class FileController {
	@RequestMapping(value="/file.f",produces="text/html;charset=utf-8")
	public String list(HttpServletRequest req) throws IllegalStateException, IOException {// req(요청에 대한 모든정보) , res
		//파일이 있는 상태의 요청을 받았는지에 따라서 유동적으로 MultipartRequest로 캐스팅
		//파일을 빼오고 저장하기.
		//Middle/img/파일명을 크롬으로 요청하면 열리게하기.
		//실제 파일은 D:\Android\폴더
//		{file=[MultipartFile[field="file", filename=test.jpg, contentType=image/jpeg, size=145249]]}
		System.out.println(req.getLocalAddr());
		System.out.println(req.getLocalPort());
		System.out.println(req.getContextPath()+"/폴더");
		MultipartRequest mReq = (MultipartRequest) req; // file정보가 없는 req => 있는 mReq
		MultipartFile file = mReq.getFile("file");
		if(file != null) {
			file.transferTo(new File("D:\\Study_Android\\97.images\\pepe1","andimg.jpg"));
		}else {

		}

		return new Gson().toJson("");
	}
}
