package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
	private int id; // 댓글 고유 번호
	private String regDate; // 등록 날짜
	private String updateDate; // 수정날짜
	private int memberId; // 작성자 회원 번호
	private String relTypeCode; // 관련된 타입의 번호 (게시글의 번호 또는 댓글의 번호)
	private int relId;// 관련된 게시글이나 댓글의 고유 번호 (id)
	private String body;// 댓글 내용
	private int goodReactionPoint; // 댓글 좋아요 수
	private int badReactionPoint; // 댓글 싫어요 수

	private String extra__writer; //댓글의 작성자의 닉네임을 따로 불러옴
 
	private boolean userCanModify;// 댓글을 수정할수 있는지 여부 (true , false)
	private boolean userCanDelete;// 댓글을 삭제 할수 있는지 여부(ture, false)
}