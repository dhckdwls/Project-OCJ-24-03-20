package com.example.demo.controller;

import com.example.demo.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CSVController {

    @Autowired
    private CSVService csvService;
    //csvsevice 연결

    @GetMapping("/readAndSaveToDB")
    @ResponseBody
    public String readAndSaveToDB() {
        return csvService.readAndSaveToDB();
    }
}

/*
 
 localhost:8081/readAndSaveToDB 를 url에 입력하면 준비된 CSV 파일들을 DB에 저장하는 로직 실행
 
 CSV 파일들 위치 -> src/main/resources/CSV 폴더안에 존재
 static 폴더에 넣어놓은 줄 알았으나 그게 아니였음
 보통 static 폴더에는 변하지 않는 폴더들을 넣어 둔다고 들어서 여기에 만듬.
 
 
  
  */
 