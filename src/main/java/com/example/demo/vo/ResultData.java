package com.example.demo.vo;

import java.util.Map;

import com.example.demo.util.Ut;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultData<DT> {
	
    private String resultCode; // 결과 코드 (예: 성공 또는 실패 코드)
    private String msg; // 결과 메시지
    private DT data1; // 첫 번째 데이터
    private String data1Name; // 첫 번째 데이터의 이름
    private Object data2; // 두 번째 데이터
    private String data2Name; // 두 번째 데이터의 이름
    private Map<String, Object> body; // 추가 데이터를 담는 맵

    // 생성자: 결과 코드와 메시지, 가변 인수를 받아 body 맵을 초기화
    public ResultData(String resultCode, String msg, Object... args) {
        this.resultCode = resultCode;
        this.msg = msg;
        this.body = Ut.mapOf(args);
    }

    // 결과 코드와 메시지만을 받아 ResultData 객체를 생성하는 정적 메소드
    public static <DT> ResultData<DT> from(String resultCode, String msg) {
        return from(resultCode, msg, null, null);
    }

    // 결과 코드, 메시지, 첫 번째 데이터 이름과 데이터를 받아 ResultData 객체를 생성하는 정적 메소드
    public static <DT> ResultData<DT> from(String resultCode, String msg, String data1Name, DT data1) {
        ResultData<DT> rd = new ResultData<DT>();
        rd.resultCode = resultCode;
        rd.msg = msg;
        rd.data1Name = data1Name;
        rd.data1 = data1;

        return rd;
    }

    // 결과 코드, 메시지, 첫 번째와 두 번째 데이터 이름과 데이터를 받아 ResultData 객체를 생성하는 정적 메소드
    public static <DT> ResultData<DT> from(String resultCode, String msg, String data1Name, DT data1, String data2Name,
                                           DT data2) {
        ResultData<DT> rd = new ResultData<DT>();
        rd.resultCode = resultCode;
        rd.msg = msg;
        rd.data1Name = data1Name;
        rd.data1 = data1;
        rd.data2Name = data2Name;
        rd.data2 = data2;

        return rd;
    }

    // 기존 ResultData 객체에 새로운 데이터를 추가하여 새로운 ResultData 객체를 생성하는 정적 메소드
    public static <DT> ResultData<DT> newData(ResultData rd, String dataName, DT newData) {
        return from(rd.getResultCode(), rd.getMsg(), dataName, newData);
    }

    // 결과가 성공인지 확인하는 메소드 (결과 코드가 "S-"로 시작하면 성공)
    public boolean isSuccess() {
        return resultCode.startsWith("S-");
    }

    // 결과가 실패인지 확인하는 메소드 (결과 코드가 "S-"로 시작하지 않으면 실패)
    public boolean isFail() {
        return isSuccess() == false;
    }

    // 두 번째 데이터를 설정하는 메소드
    public void setData2(String data2Name, Object data2) {
        this.data2Name = data2Name;
        this.data2 = data2;
    }
}