package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    private int id; // 게시판 ID
    private String regDate; // 생성 날짜
    private String updateDate; // 수정 날짜
    private String code; // 게시판 코드
    private String name; // 게시판 이름
    private boolean delStatus; // 삭제 상태 (true: 삭제됨, false: 삭제되지 않음)
    private String delDate; // 삭제 날짜 (삭제된 경우에만 사용)
    
    //deltstatus로 게시판이 삭제가 된 상태라면 alert이라던지 exception으로 처리해서 접근을 제한해 둘수 있다
}