package com.example.demo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenFile {
    private int id; // 파일 ID
    private String regDate; // 파일 등록 날짜
    private String updateDate; // 파일 수정 날짜
    private boolean delStatus; // 삭제 상태 (true: 삭제됨, false: 삭제되지 않음)
    private String delDate; // 삭제 날짜 (삭제된 경우에만 사용)
    private String typeCode; // 파일 유형 코드
    private String type2Code; // 파일 세부 유형 코드
    private String relTypeCode; // 관련 유형 코드
    private int relId; // 관련 ID
    private String fileExtTypeCode; // 파일 확장자 유형 코드
    private String fileExtType2Code; // 파일 세부 확장자 유형 코드
    private int fileSize; // 파일 크기 (바이트 단위)
    private int fileNo; // 파일 번호
    private String fileExt; // 파일 확장자
    private String fileDir; // 파일이 저장된 디렉토리 경로
    private String originFileName; // 원본 파일 이름

    // 파일 경로를 반환 (이 메소드는 JSON으로 변환될 때 무시됨)
    @JsonIgnore
    public String getFilePath(String genFileDirPath) {
        return genFileDirPath + getBaseFileUri();
    }

    // 기본 파일 URI를 반환 (이 메소드는 JSON으로 변환될 때 무시됨)
    @JsonIgnore
    private String getBaseFileUri() {
        return "/" + relTypeCode + "/" + fileDir + "/" + getFileName();
    }

    // 파일 이름을 반환
    public String getFileName() {
        return id + "." + fileExt;
    }

    // 출력용 URL을 반환
    public String getForPrintUrl() {
        return "/gen" + getBaseFileUri() + "?updateDate=" + updateDate;
    }

    // 파일 다운로드 URL을 반환
    public String getDownloadUrl() {
        return "/common/genFile/doDownload?id=" + id;
    }

    // 미디어 HTML 태그를 반환 (이미지 태그)
    public String getMediaHtml() {
        return "<img src=\"" + getForPrintUrl() + "\">";
    }
}