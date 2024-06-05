package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.APITestService;

@RestController
@RequestMapping("/save")
public class APIController {

	private final APITestService apiTestService;
	//apiservice와 연결

	@Autowired
	public APIController(APITestService apiTestService) {
		this.apiTestService = apiTestService;
	}
	//생성자를 통해서 apitestservice를 생성

	@GetMapping("/")
	public String fetchAndSaveData() {
		return apiTestService.fetchAndSaveData();
		//apiservice의 fetchAndSaveData 실행 리턴값을 반환
	}
}

/*
localhost:8081/save/ 라고 입력하게 되면 한국관광공사 국문관광정보 서비스 api를 호출하는 서비스를 불러온다



*/
 