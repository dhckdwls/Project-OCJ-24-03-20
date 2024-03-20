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
			
			address1 = #{address1},
			address2 = #{address2},
			mapX = #{mapX},
			mapY = #{mapY},
			firstImage = #{firstImage},
			firstImage2 = #{firstImage2},
			
			tag = '#여행#테스트#케이스#서울',
			
			hitcount = 0,
			goodReactionPoint = 0
			""")
	public void saveData(String title, int areaCode, int contentTypeId, String address1, String address2, String mapX,
			String mapY, String firstImage, String firstImage2, String contentId);

	

}
