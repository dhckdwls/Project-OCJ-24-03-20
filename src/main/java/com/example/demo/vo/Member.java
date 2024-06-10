package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	private int id; //고유 번호
	private String regDate; // 등록날짜
	private String updateDate;// 수정 날짜
	private String loginId; // 로그인 아이디
	private String loginPw; // 로그인 비밀번호
	private int authLevel;  //권한 레벨
	private String name;  //이름 
	private String nickname; //닉네임
	private String cellphoneNum; //전화번호
	private String email; //이메일주소
	private boolean delStatus; //탈퇴 여부 (true false)
	private String delDate; // 탈퇴 날짜

}