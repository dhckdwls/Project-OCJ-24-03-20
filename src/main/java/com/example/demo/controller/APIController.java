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

	@Autowired
	public APIController(APITestService apiTestService) {
		this.apiTestService = apiTestService;
	}

	@GetMapping("/")
	public String fetchAndSaveData() {
		return apiTestService.fetchAndSaveData();
		//return "데이터를 성공적으로 가져오고 저장했습니다!";
	}
}