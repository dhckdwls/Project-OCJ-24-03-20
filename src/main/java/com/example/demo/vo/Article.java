package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private int id; // article 고유 번호 ID
    private String regDate; // 등록 날짜
    private String updateDate; // 수정 날짜
    private int memberId; // 작성자 회원 ID
    private int boardId; // 게시판 ID
    
    private String title; // 게시물 제목
    private String body; // 게시물 내용
    
    private int areaCode; // 지역 코드
    private int contentTypeId; // 콘텐츠 유형 ID
    private int contentId; // 콘텐츠 ID
    
    private String address; // 주소
    private String mapX; // 지도 X 좌표
    private String mapY; // 지도 Y 좌표
    
    private String firstImage; // 첫 번째 이미지 URL
    private String firstImage2; // 두 번째 이미지 URL
    
    private String tag; // 태그
    
    private int hitCount; // 조회수
    private int goodReactionPoint; // 좋아요 수
    
    private int extra__repliesCnt; // 댓글 수
	private String extra__writer; // 작성자 이름
	
    private boolean userCanModify; // 수정 권한 여부 (true/false)
	private boolean userCanDelete; // 삭제 권한 여부 (true/false)
}