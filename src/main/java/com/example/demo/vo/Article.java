package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private int id;
    private String regDate; // regDate 필드 추가
    private String updateDate;
    private int memberId;
    private int boardId;
    
    private String title;
    private String body;
    
    private int areaCode;
    private int contentTypeId;
    private int contentId;
    
    private String address;
    private String mapX;
    private String mapY;
    
    private String firstImage;
    private String firstImage2;
    
    private String tag;
    
    private int hitCount;
    private int goodReactionPoint;
    
    private int extra__repliesCnt;
	private String extra__writer;
	
    private boolean userCanModify;
	private boolean userCanDelete;
}