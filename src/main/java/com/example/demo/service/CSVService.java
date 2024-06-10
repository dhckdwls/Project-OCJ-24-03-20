package com.example.demo.service;

import com.example.demo.repository.CSVRepository;
import com.example.demo.vo.CSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
//java 에서 csv 파일을 읽어올수 있는 라이브러리 사용
//사용 이유 mysql 에서도 csv 파일들을 직접 데이터 불러오기로 넣을수 있었음
//그러나 그 방법을 몰라서 한파일당 100개 총 25000개의 데이터를 손수 넣어야 하는 상황 발생
//인터넷에서 찾아본 결과 opencsv라는 라이브러리가 존재 이는 자바 내에서 csv 파일을 읽어들일수 있는 기능이 있다고 해서
//검색 후 사용하기로 결정

@Service
public class CSVService {

	@Autowired
	private CSVRepository csvRepository;

	public String readAndSaveToDB() {
		try {
			List<CSV> csvList = new ArrayList<>();
			//빈 배열 csvList 생성

			// 예시 파일들을 배열에 추가
			String[] fileNames = { "test1.csv", "test2.csv", "test3.csv", "test4.csv", "test5.csv" };

			// 파일 이름들을 배열에 추가
//            String[] fileNames = new String[200];
//            for (int i = 1; i <= 200; i++) {
//                fileNames[i - 1] = "test" + i + ".csv";
//            }
			
			// 250개의 파일을 DB에 저장하려면 용량문제로 되지 않음 그래서 일단 5개의 파일만 실행
			// 위의 test1.csv 는 파일이름 

			for (String fileName : fileNames) {
				InputStreamReader is = new InputStreamReader(
						getClass().getClassLoader().getResourceAsStream("CSV/" + fileName), "EUC-KR");
				CSVReader reader = new CSVReader(is);
				// 파일경로 src/main/resources/CSV/ + 파일이름 (여기서 파일이름은 위의 예시파일들의 이름을 임의로 넣어준것이랑 동일)
				//언어 korea로 설정
				reader.skip(1);
				// 첫 번째 줄(헤더) 건너뛰기
				//CSV파일 첫번째 줄에는 지역 순위 주소 방문자 수 로 data의 내용이 아닌 구분위한 column이 한줄 있다
				// 이 한줄을 제외하고 가져오기 위해서 skip 1 첫번째줄 생략 하는 것이다

				List<String[]> list = reader.readAll();
				//이 리스트에는 csv파일들의 데이터의 모든것들이 존재
				//아래 반복문을 통해 필요한 정보만 추출
				for (String[] csvRow : list) {
					CSV csv = new CSV();
					//미리 만들어 놓은 csv 클래스
					// 엔터티의 필드에 CSV 데이터를 할당
					csv.setRanking(Integer.parseInt(csvRow[0])); // 정수형으로 변환
					csv.setTitle(csvRow[1]);
					csv.setAddress(csvRow[2]);
					csv.setType(csvRow[3]);
					csv.setCount(Integer.parseInt(csvRow[4]));
					//랭킹 , 제목, 주소 , 관광지타입, 방문자수 를 추출
					//parseint는 정수형으로 바꿔주고 그 외에는 String 타입
					//위에서 만든 csv객체를 csv리스트에 저장
					csvList.add(csv);
					//
				}
			}

			// CSV 데이터를 데이터베이스에 저장
			csvRepository.insertCSVList(csvList);
			//csv 리스트를 리포지터리에 넘겨서 리포지터리에서 mybatis 문법으로 반복문을 통해 csv객체의 데이터를 DB에 저장

			return "CSV 데이터가 성공적으로 데이터베이스에 저장되었습니다.";
			//성공시 문구

		} catch (Exception e) {
			e.printStackTrace();
			return "CSV 데이터를 데이터베이스에 저장하는 중 오류가 발생했습니다.";
			//실패시 문구
		}
	}
}