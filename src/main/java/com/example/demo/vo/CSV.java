package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CSV {

    private int id; // 기본키 필드
    private int ranking;
    private String title;
    private String address;
    private String type;
    private int count;
}