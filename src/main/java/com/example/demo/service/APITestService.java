package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.repository.APITestRepository;
import com.example.demo.util.Ut;
import com.example.demo.vo.APIarticle;
import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class APITestService {

	private final APITestRepository apiTestRepository;

	@Autowired
	public APITestService(APITestRepository apiTestRepository) {
		this.apiTestRepository = apiTestRepository;
	}

	public String fetchAndSaveData() {
		String serviceKey = "7gBxrsj7WSHvOZjYdEQXGXuT9pq9L8NMGDZ9hzG7VnyftpPH7IIKkWxq2HkS94X9AsKLEzXCkaOZeH94lv28Bg==";
		String apiUrl = "http://apis.data.go.kr/B551011/KorService1/areaBasedList1";
		int pageNo = 1;
		//int numOfRows = 13040;
		int numOfRows = 10;
		String arrange = "A"; // (A=제목순, C=수정일순, D=생성일순)
		String areaCode = ""; // 필요에 따라 변경하세요
		int contentTypeId = 12; // 관광 명소에 해당하는 contentTypeId
		String mobileApp = "AppTest";
		String mobileOS = "ETC";

		StringBuilder apiUrlWithParamsBuilder = new StringBuilder(apiUrl);
		apiUrlWithParamsBuilder.append("?serviceKey=").append(serviceKey).append("&pageNo=").append(pageNo)
				.append("&numOfRows=").append(numOfRows).append("&MobileApp=").append(mobileApp).append("&MobileOS=")
				.append(mobileOS).append("&arrange=").append(arrange).append("&areaCode=").append(areaCode)
				.append("&contentTypeId=").append(contentTypeId).append("&_type=json");

		String apiUrlWithParams = apiUrlWithParamsBuilder.toString();

		RestTemplate restTemplate = new RestTemplate();
		String responseData = restTemplate.getForObject(apiUrlWithParams, String.class);

		// JSON 데이터 처리
		try {
			// JSON 데이터를 APIarticle 객체로 변환하여 저장
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(responseData);
			JsonNode itemNode = rootNode.path("response").path("body").path("items").path("item");

			for (JsonNode node : itemNode) {
				String title = node.path("title").asText();
				int areacode = node.path("areacode").asInt();
				int contenttypeid = node.path("contenttypid").asInt();
				String addr1 = node.path("addr1").asText();
				String addr2 = node.path("addr2").asText();
				String mapx = node.path("mapx").asText();
				String mapy = node.path("mapy").asText();
				String firstimage = node.path("firstimage").asText();
				String firstimage2 = node.path("firstimage2").asText();
				int mlevel = node.path("mlevel").asInt();
				String tel = node.path("tel").asText();
				int zipcode = node.path("zipcode").asInt();
				
				apiTestRepository.saveData(title,areacode,contenttypeid,addr1,addr2,mapx,mapy,firstimage,firstimage2,mlevel,tel,zipcode);
				
				
				
				
				
				
				
				
//				System.err.println(node.path("title").asText());
				
//                article.setTitle(node.path("title").asText());
//                article.setAreacode(node.path("areacode").asInt());
//                article.setContenttypeid(node.path("contenttypeid").asInt());
//                article.setAddr1(node.path("addr1").asText());
//                article.setAddr2(node.path("addr2").asText());
//                article.setMapx(node.path("mapx").asText());
//                article.setMapy(node.path("mapy").asText());
//                article.setFirstimage(node.path("firstimage").asText());
//                article.setFirstimage2(node.path("firstimage2").asText());
//                article.setMlevel(node.path("mlevel").asInt());
//                article.setTel(node.path("tel").asText());
//                article.setZipcode(node.path("zipcode").asInt());
//                article.setHitCount(0); // hitCount 값을 0으로 설정

				// 나머지 필드는 원하는 값으로 설정해야 합니다.

				// apiTestRepository.saveData(article);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseData;
	}

	public List<APIarticle> getForPrintAPITestArticles() {
		List<APIarticle> apiarticle = apiTestRepository.getForPrintAPITestArticles();
		return apiarticle;
	}

	

	public APIarticle getForPrintAPIArticle(int loginedMemberId, int id) {
		APIarticle aPIarticle = apiTestRepository.getForPrintAPIarticle(id);
		
		controlForPrintData(loginedMemberId, aPIarticle);
		return aPIarticle;
	}

	private void controlForPrintData(int loginedMemberId, APIarticle aPIarticle) {
		if (aPIarticle == null) {
			return;
		}
		
		ResultData userCanModifyRd = userCanModify(loginedMemberId, aPIarticle);
		aPIarticle.setUserCanModify(userCanModifyRd.isSuccess());

		ResultData userCanDeleteRd = userCanDelete(loginedMemberId, aPIarticle);
		aPIarticle.setUserCanDelete(userCanDeleteRd.isSuccess());
		
	}
	
	public ResultData userCanDelete(int loginedMemberId, APIarticle aPIarticle) {

		if (aPIarticle.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("%d번 글에 대한 삭제 권한이 없습니다", aPIarticle.getId()));
		}

		return ResultData.from("S-1", Ut.f("%d번 글이 삭제 되었습니다", aPIarticle.getId()));
	}

	public ResultData userCanModify(int loginedMemberId, APIarticle aPIarticle) {

		if (aPIarticle.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("%d번 글에 대한 수정 권한이 없습니다", aPIarticle.getId()));
		}

		return ResultData.from("S-1", Ut.f("%d번 글을 수정했습니다", aPIarticle.getId()));
	}
	
	
}