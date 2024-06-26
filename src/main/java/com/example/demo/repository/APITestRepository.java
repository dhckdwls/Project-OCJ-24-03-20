package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.vo.Article;

@Mapper
public interface APITestRepository {
	
	@Insert("""
			INSERT INTO article
			SET regDate = NOW(),
			updateDate = NOW(),
			memberId = 1,
			boardId = 1,
			
			title = #{title},
			`body` = '준비된 내용이 없습니다',
			areaCode = #{areaCode},
			contentTypeId = #{contentTypeId},
			contentId = #{contentId},
			
			address = #{address},
			mapX = #{mapX},
			mapY = #{mapY},
			firstImage = #{firstImage},
			firstImage2 = #{firstImage2},
			
			tag = '#여행#테스트#케이스#서울',
			
			hitcount = 0,
			goodReactionPoint = 0
			""")
	public void saveData(String title, int areaCode, int contentTypeId, String address, String mapX,
			String mapY, String firstImage, String firstImage2, String contentId);

	//service에서 인자로 받은 여행지의 title,지역코드 , 컨텐트타입, 주소, x좌표, y좌표, 사진 url 두개, 컨텐트 아이디를 article에 저장

}
