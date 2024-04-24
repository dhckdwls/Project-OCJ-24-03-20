package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.MapService;

@Controller
public class MapController {
	
	@Autowired
	private MapService mapService;
	
	@RequestMapping("/findRoute")
	public void findRoute(String startAddress, String endAddress) {
		
		
	}

}
