package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CSV {

    private int id; // 기본키 필드
    private int ranking; //순위
    private String title; //이름
    private String address;//주소
    private String type;//타입 ex)쇼핑 관광 등
    private int count;//방문자수
}