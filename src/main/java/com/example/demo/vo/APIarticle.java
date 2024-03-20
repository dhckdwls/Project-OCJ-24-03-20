package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIarticle {
    private int id;
    private String regDate; // regDate 필드 추가
    private String updateDate;
    private int memberId;
    
    private String title;
    private String body;
    
    private int areacode;
    private int contenttypeid;
    
    private String addr1;
    private String addr2;
    private String mapx;
    private String mapy;
    
    private String firstimage;
    private String firstimage2;
    
    private String tag;
    
    private int mlevel;//필요없음
    private String tel;//필요없음
    private int zipcode;//우편번호
    
    private int hitCount;
    
    
    
    private boolean userCanModify;
	private boolean userCanDelete;
}