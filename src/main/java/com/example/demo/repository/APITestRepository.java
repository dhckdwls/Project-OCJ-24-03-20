package com.example.demo.repository;

import com.example.demo.vo.APIarticle;
import com.example.demo.vo.Article;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface APITestRepository {
	
	@Insert("""
			INSERT INTO APIarticle
			SET regdate = NOW(),
			updateDate = NOW(),
			memberId = 1,
			
			title = #{title},
			`body` = '준비된 내용이 없습니다',
			areacode = #{areacode},
			contenttypeid = #{contenttypeid},
			addr1 = #{addr1},
			addr2 = #{addr2},
			mapx = #{mapx},
			mapy = #{mapy},
			firstimage = #{firstimage},
			firstimage2 = #{firstimage2},
			mlevel = #{mlevel},
			tel = #{tel},
			zipcode = #{zipcode},
			hitcount = 0
			""")
	public void saveData(String title, int areacode, int contenttypeid, String addr1, String addr2, String mapx,
			String mapy, String firstimage, String firstimage2, int mlevel, String tel, int zipcode);

	@Select("""
			SELECT * FROM apiarticle
			
			""")
	public List<APIarticle> getForPrintAPITestArticles();

	
	
	@Select("""
			<script>
				SELECT A.*, M.nickname AS extra__writer
				FROM APIarticle AS A
				INNER JOIN `member` AS M
				ON A.memberId = M.id
				WHERE A.id = #{id}
				GROUP BY A.id
			</script>
				""")
	public APIarticle getForPrintAPIarticle(int id);
	

}
