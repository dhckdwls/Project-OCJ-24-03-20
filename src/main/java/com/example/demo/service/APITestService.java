package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.repository.APITestRepository;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;
//api request 후 반환받는 response data가 json 형식일때 이를 java에서 parsing 하기 위해서 라이브러리 추가
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class APITestService {

	private final APITestRepository apiTestRepository;
	//apitestRepository 상수 선언

	@Autowired
	public APITestService(APITestRepository apiTestRepository) {
		this.apiTestRepository = apiTestRepository;
	}
	//생성자를 통해서 apitestrepository 값 채우기

	public String fetchAndSaveData() {
		String serviceKey = "7gBxrsj7WSHvOZjYdEQXGXuT9pq9L8NMGDZ9hzG7VnyftpPH7IIKkWxq2HkS94X9AsKLEzXCkaOZeH94lv28Bg==";
		//공공데이터포털에 한국관광공사 api를 호출하기 위한 service key 위 값은 decoding service key
		//encoding key 는 무슨이유인지 모르겠지만 동작하지 않음 => 그에 따라 response data가 존재하지 않음
		String apiUrl = "http://apis.data.go.kr/B551011/KorService1/areaBasedList1";
		//api를 호출하기 위한 url
		
		//api 호출시 요구하는 파라미터들
		int pageNo = 1;
		//response의 데이터중 1번 페이지의 값을 원한다
		 
		int numOfRows = 50;
		//int numOfRows = 13040; => 총 개수
		//api 호출시 몇 행 즉 몇개의 데이터를 가져올 것인지 설정
		//database = mysql 사용중인데 13040개의 데이터를 가져오면 DB 에서 저장용량을 초과 따라서 예시로 50개만 가져오도록 설정
		String arrange = "A"; 
		//데이터들을 어떤 방식의 정렬로 가져올 것인지 설정
		// (A=제목순, C=수정일순, D=생성일순)
		
		String areaCode1 = ""; // 필요에 따라 변경하세요
		//지역코드 서울 1 경기 2 부산 14 등등 각 행정구역별 지역코드 존재
		//모든지역의 여행지를 가져오기 위해서 areacode는 비운다
		int contentTypeId1 = 12;
		// 관광 명소에 해당하는 contentTypeId
		//쇼핑 13 등등 각 컨텐츠 타입에 맞는 번호 존재
		//여행지에 대한 부분만 필요해서 12로 고정
		String mobileApp = "AppTest";
		String mobileOS = "ETC";
		//필수 입력사항인데 그냥 기본값으로 설정

		StringBuilder apiUrlWithParamsBuilder = new StringBuilder(apiUrl);
		apiUrlWithParamsBuilder.append("?serviceKey=").append(serviceKey).append("&pageNo=").append(pageNo)
				.append("&numOfRows=").append(numOfRows).append("&MobileApp=").append(mobileApp).append("&MobileOS=")
				.append(mobileOS).append("&arrange=").append(arrange).append("&areaCode=").append(areaCode1)
				.append("&contentTypeId=").append(contentTypeId1).append("&_type=json");

		String apiUrlWithParams = apiUrlWithParamsBuilder.toString();
		
		//api를 호출하기 위한 파라미터들을 url과 조합해서 호출 => stringbuilder를 활용해서 조합

		RestTemplate restTemplate = new RestTemplate();
		String responseData = restTemplate.getForObject(apiUrlWithParams, String.class);

		// JSON 데이터 처리
		try {
			// JSON 데이터를 APIarticle 객체로 변환하여 저장
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(responseData);
			JsonNode itemNode = rootNode.path("response").path("body").path("items").path("item");
			//response 안에는 여러 객체들이 존재 header body resultcode 등등 
			//내가 필요한 정보는 response 안에 body에 items 안에 item 안에 각 여행지에 대한 정보들이 존재

			for (JsonNode node : itemNode) {
				String title = node.path("title").asText();
				int areaCode = node.path("areacode").asInt();
				int contentTypeId = node.path("contenttypeid").asInt();
				String address = node.path("addr1").asText() + node.path("addr2").asText();
				String mapX = node.path("mapx").asText();
				String mapY = node.path("mapy").asText();
				String firstImage = node.path("firstimage").asText();
				String firstImage2 = node.path("firstimage2").asText();
				String contentId = node.path("contentid").asText();
				
				//50개의 여행지를 받아오는데 이를 반복문을 통해서 json 데이터들을 java 형식에 맞게 변환해서 변수에 저장
				//이를 apirepository의 savedata를 통해서 article로 저장
				apiTestRepository.saveData(title,areaCode,contentTypeId,address,mapX,mapY,firstImage,firstImage2,contentId);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseData;
		//api request를 통해서 받은 response data를 확인하기 위해서 return값을 responsedata로 설정
		//여기서 문제점
		//그전까지는 responsedata를 찍어보지 않고 계속 왜 안돼지 쓸데없이 시간낭비하면서 실행만 함
		// 그 후 공공데이터포털에서 test를 해볼수 있게 되어 있엇는데 본인의 공공데이터포털 encoding service key가 먹통임
		// 그 후 decoding service key 로 교체 잘 동작 하는것을 확인 후 apiservice 의 servicekey를 decoding key로 변경
	}


	


	
	
	
}