package com.example.demo.service;

import com.example.demo.repository.CSVRepository;
import com.example.demo.vo.CSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

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