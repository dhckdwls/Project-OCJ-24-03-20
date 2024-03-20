package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripArticle {
	private int id;
	private String regDate;
	private String updateDate;
	private int memberId;
	private String title;
	
	private int contentTypeId;
	private int areaCode;
	private String image;
	private String image2;
	private String mapx;
	private String mapy;
	private String addr1;
	private String addr2;
	private String tel;
	private String zipcode;
	private int mlevel;
	
	
	
	
	
	
	private int hitCount;
	private int goodReactionPoint;
	private int badReactionPoint;

	private int extra__repliesCnt;
	private String extra__writer;

	private boolean userCanModify;
	private boolean userCanDelete;
}