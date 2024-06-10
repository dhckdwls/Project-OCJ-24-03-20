package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.vo.Article;

@Mapper
public interface ArticleRepository {
	
	//마지막으로 등록된 article의 id를 가져온다
	@Select("SELECT LAST_INSERT_ID()")
	public int getLastInsertId();

	//id에 맞는 article의 전체 데이터를 가져온다
	@Select("""
			SELECT *
			FROM article
			WHERE id = #{id}
			""")
	public Article getArticle(int id);
	
	//getforprint 출력하기 위한
	//article과 member를 join해서 데이터를 가져온다
	//작성자의 닉네임을 작성자로 해서
	//article의 정보는 모든것을 가져온다
	@Select("""
			<script>
				SELECT A.*, M.nickname AS extra__writer
				FROM article AS A
				INNER JOIN `member` AS M
				ON A.memberId = M.id
				WHERE A.id = #{id}
				GROUP BY A.id
			</script>
				""")
	public Article getForPrintArticle(int id);
	
	
	//id에 맞는 article을 삭제
	@Delete("DELETE FROM article WHERE id = #{id}")
	public void deleteArticle(int id);
	
	
	//id에 맞는 article을 수정하기 위해
	//공백방지까지 추가
	@Update("""
			<script>
			UPDATE article
				<set>
					<if test="title != null and title != ''">title = #{title},</if>
					<if test="body != null and body != ''">`body` = #{body},</if>
					updateDate = NOW()
				</set>
			WHERE id = #{id}
			</script>
				""")
	public void modifyArticle(int id, String title, String body);
	
	//article에 작성자의 닉네임까지 join해서 가져온다
	//여러개를 가져와서 반환 
	@Select("""
			SELECT A.*, M.nickname AS extra__writer
			FROM article AS A
			INNER JOIN `member` AS M
			ON A.memberId = M.id
			ORDER BY A.id DESC
			""")
	public List<Article> getArticles();

	/*
	 * Article 테이블에서 게시물 수를 조회하는 MyBatis 매퍼입니다.
	 * boardId 게시판 ID
	 * searchKeywordTypeCode 검색 키워드 유형 코드
	 * searchKeyword 검색 키워드
	 */
	@Select("""
			<script>
			SELECT COUNT(*) AS cnt
			FROM article AS A
			WHERE 1
			<if test="boardId != 0">
				AND boardId = #{boardId}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'title'">
						AND A.title LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<when test="searchKeywordTypeCode == 'body'">
						AND A.body LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<when test="searchKeywordTypeCode == 'tag'">
						AND A.tag LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<when test="searchKeywordTypeCode == 'address'">
						AND A.address LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<otherwise>
						AND A.title LIKE CONCAT('%',#{searchKeyword},'%')
						OR A.body LIKE CONCAT('%',#{searchKeyword},'%')
						OR A.tag LIKE CONCAT('%',#{searchKeyword},'%')
						OR A.address LIKE CONCAT('%',#{searchKeyword},'%')
					</otherwise>
				</choose>
			</if>
			ORDER BY id DESC
			</script>
			""")
	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword);

	@Update("""
			UPDATE article
			SET hitCount = hitCount + 1
			WHERE id = #{id}
			""")
	public int increaseHitCount(int id);

	@Select("""
			SELECT hitCount
			FROM article
			WHERE id = #{id}
			""")
	public int getArticleHitCount(int id);

	@Select("""
			<script>
			SELECT A.*, M.nickname AS extra__writer, IFNULL(COUNT(R.id),0) AS extra__repliesCnt
			FROM article AS A
			INNER JOIN `member` AS M
			ON A.memberId = M.id
			LEFT JOIN `reply` AS R
			ON A.id = R.relId
			WHERE 1
			<if test="boardId != 0">
				AND A.boardId = #{boardId}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'title'">
						AND A.title LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<when test="searchKeywordTypeCode == 'body'">
						AND A.body LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<when test="searchKeywordTypeCode == 'tag'">
						AND A.tag LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<when test="searchKeywordTypeCode == 'address'">
						AND A.address LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<otherwise>
						AND A.title LIKE CONCAT('%',#{searchKeyword},'%')
						OR A.body LIKE CONCAT('%',#{searchKeyword},'%')
						OR A.tag LIKE CONCAT('%',#{searchKeyword},'%')
						OR A.address LIKE CONCAT('%',#{searchKeyword},'%')
					</otherwise>
				</choose>
			</if>
			GROUP BY A.id
			ORDER BY A.id DESC
			<if test="limitFrom >= 0 ">
				LIMIT #{limitFrom}, #{limitTake}
			</if>
			</script>
			""")
	public List<Article> getForPrintArticles(int boardId, int limitFrom, int limitTake, String searchKeywordTypeCode,
			String searchKeyword);

	@Update("""
			UPDATE article
			SET goodReactionPoint = goodReactionPoint + 1
			WHERE id = #{relId}
			""")
	public int increaseGoodReactionPoint(int relId);

	@Update("""
			UPDATE article
			SET goodReactionPoint = goodReactionPoint - 1
			WHERE id = #{relId}
			""")
	public int decreaseGoodReactionPoint(int relId);

	@Update("""
			UPDATE article
			SET badReactionPoint = badReactionPoint + 1
			WHERE id = #{relId}
			""")
	public int increaseBadReactionPoint(int relId);

	@Update("""
			UPDATE article
			SET badReactionPoint = badReactionPoint - 1
			WHERE id = #{relId}
			""")
	public int decreaseBadReactionPoint(int relId);

	@Select("""
			SELECT goodReactionPoint
			FROM article
			WHERE id = #{relId}
			""")
	public int getGoodRP(int relId);



	@Insert("""
			INSERT INTO article
			SET regdate = NOW(),
			updateDate = NOW(),
			memberId = #{memberId},
			boardId = #{boardId},

			title = #{title},
			`body` = #{body},
			areaCode = -10,
			contentTypeId = #{contentTypeId},
			address = #{address},
			mapX = #{mapX},
			mapY = #{mapY},
			firstImage = #{firstImage},
			firstImage2 = #{firstImage2},

			tag = #{tag},

			goodReactionPoint = 0,
			hitcount = 0
			""")
	public void writeArticle(int boardId, int memberId, int contentTypeId, String title, String body, String tag,
			String firstImage, String firstImage2, String address, String mapX, String mapY);
	
	//게시물의 모든 주소를 가져오기
	@Select("""
			SELECT address FROM article
			""")
	public String[] getArticlesAddress();
	
	//게시물의 모든 제목 가져오기
	@Select("""
			SELECT title FROM article
			""")
	public String[] getArticlesTitles();

	@Select("""
			SELECT * FROM article
			WHERE memberId = #{memberId}
			""")
	public List<Article> geyMyArticles(int memberId);

	@Select("""
			SELECT A.*
			FROM article AS A
			INNER JOIN reactionPoint AS R
			ON A.id = R.relId
			WHERE R.memberId = #{memberId}
			""")
	public List<Article> getLikeArticles(int memberId);

	@Select("""
			SELECT MAX(id) + 1
			FROM article
			""")
	public int getCurrentArticleId();

	@Select("""
			SELECT A.*,
			(SELECT COUNT(*) FROM reply AS R WHERE R.relId = A.id AND R.relTypeCode = 'article') AS extra__repliesCnt
			FROM article AS A
			ORDER BY RAND()
			LIMIT 1;
			""")
	public Article getRandomArticle();
	
	@Select("""
			SELECT tag FROM article
			""")
	public List<String> getArticlesTags();

}