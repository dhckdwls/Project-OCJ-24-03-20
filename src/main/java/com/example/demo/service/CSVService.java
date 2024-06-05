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

			// 예시 파일들을 배열에 추가
			String[] fileNames = { "test1.csv", "test2.csv", "test3.csv", "test4.csv", "test5.csv" };

			// 파일 이름들을 배열에 추가
//            String[] fileNames = new String[200];
//            for (int i = 1; i <= 200; i++) {
//                fileNames[i - 1] = "test" + i + ".csv";
//            }

			for (String fileName : fileNames) {
				InputStreamReader is = new InputStreamReader(
						getClass().getClassLoader().getResourceAsStream("CSV/" + fileName), "EUC-KR");
				CSVReader reader = new CSVReader(is);

				// 첫 번째 줄(헤더) 건너뛰기
				reader.skip(1);

				List<String[]> list = reader.readAll();

				for (String[] csvRow : list) {
					CSV csv = new CSV();
					// 엔터티의 필드에 CSV 데이터를 할당
					csv.setRanking(Integer.parseInt(csvRow[0])); // 정수형으로 변환
					csv.setTitle(csvRow[1]);
					csv.setAddress(csvRow[2]);
					csv.setType(csvRow[3]);
					csv.setCount(Integer.parseInt(csvRow[4]));

					csvList.add(csv);
				}
			}

			// CSV 데이터를 데이터베이스에 저장
			csvRepository.insertCSVList(csvList);

			return "CSV 데이터가 성공적으로 데이터베이스에 저장되었습니다.";

		} catch (Exception e) {
			e.printStackTrace();
			return "CSV 데이터를 데이터베이스에 저장하는 중 오류가 발생했습니다.";
		}
	}
}