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
	 * Article 테이블에서 게시물 수를 조회하는 MyBatis 매퍼
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
	
	
	/*
	 * Article 테이블에서 특정 게시물의 조회 수(hitCount)를 1 증가시키는 MyBatis 매퍼
	 * id 조회 수를 증가시킬 게시물의 ID
	 */
	@Update("""
			UPDATE article
			SET hitCount = hitCount + 1
			WHERE id = #{id}
			""")
	public int increaseHitCount(int id);
	
	/*
	 * 특정 게시물의 조회 수(hitCount)를 조회하는 MyBatis 매퍼
	 * id 조회 수를 조회할 게시물의 ID
	 * 게시물의 조회 수 (hitCount)
	 */
	@Select("""
			SELECT hitCount
			FROM article
			WHERE id = #{id}
			""")
	public int getArticleHitCount(int id);
	
	/*
	 * 특정 조건에 맞는 게시물 목록을 조회하는 MyBatis 매퍼
	 * boardId 게시판 ID
	 * limitFrom 조회할 게시물의 시작 위치 (페이징 처리 시 사용)
	 * limitTake 조회할 게시물의 개수 (페이징 처리 시 사용)
	 * searchKeywordTypeCode 검색 키워드 유형 코드 (예: 'title', 'body', 'tag', 'address')
	 * searchKeyword 검색 키워드
	 * 조건에 맞는 게시물 목록 (게시물 정보, 작성자 닉네임, 댓글 수 포함)
	 */
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
	
	/*
	 * 특정 게시물의 호감 반응 점수(goodReactionPoint)를 1 증가시키는 MyBatis 매퍼
	 * relId 좋아요 수를 증가시킬 게시물의 ID
	 */
	@Update("""
			UPDATE article
			SET goodReactionPoint = goodReactionPoint + 1
			WHERE id = #{relId}
			""")
	public int increaseGoodReactionPoint(int relId);
	
	
	/*
	 * 특정 게시물의 호감 반응 점수(goodReactionPoint)를 1 감소시키는 MyBatis 매퍼
	 * relId 좋아요수를 감소시킬 게시물의 ID
	 */
	@Update("""
			UPDATE article
			SET goodReactionPoint = goodReactionPoint - 1
			WHERE id = #{relId}
			""")
	public int decreaseGoodReactionPoint(int relId);
	
	/*
	 * 특정 게시물의 비호감 반응 점수(badReactionPoint)를 1 증가시키는 MyBatis 매퍼
	 * relId 싫어요 수를 증가시킬 게시물의 ID
	 */
	@Update("""
			UPDATE article
			SET badReactionPoint = badReactionPoint + 1
			WHERE id = #{relId}
			""")
	public int increaseBadReactionPoint(int relId);
	
	/*
	 * 특정 게시물의 비호감 반응 점수(badReactionPoint)를 1 감소시키는 MyBatis 매퍼
	 * relId 비호감 반응 점수를 감소시킬 게시물의 ID
	 */
	@Update("""
			UPDATE article
			SET badReactionPoint = badReactionPoint - 1
			WHERE id = #{relId}
			""")
	public int decreaseBadReactionPoint(int relId);
	
	/*
	 * 특정 게시물의 호감 반응 점수(goodReactionPoint)를 조회하는 MyBatis 매퍼
	 * relId 호감 반응 점수를 조회할 게시물의 ID
	 */
	@Select("""
			SELECT goodReactionPoint
			FROM article
			WHERE id = #{relId}
			""")
	public int getGoodRP(int relId);


	/*
	 * 새로운 게시물을 작성하는 MyBatis 매퍼
	 * boardId 게시판 ID
	 * memberId 작성자 회원 ID
	 * contentTypeId 컨텐츠 유형 ID 관광지 유형
	 * title 게시물 제목
	 * body 게시물 내용
	 * tag 게시물 태그
	 * firstImage 게시물 첫 번째 이미지 URL
	 * firstImage2 게시물 두 번째 이미지 URL
	 * address 게시물 주소
	 * mapX 지도상의 X 좌표
	 * mapY 지도상의 Y 좌표
	 */
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
	
	//본인이 작성한 게시물 가져오기
	@Select("""
			SELECT * FROM article
			WHERE memberId = #{memberId}
			""")
	public List<Article> geyMyArticles(int memberId);
	
	//특정회원이 좋아요를 누른 게시물 가져오기
	@Select("""
			SELECT A.*
			FROM article AS A
			INNER JOIN reactionPoint AS R
			ON A.id = R.relId
			WHERE R.memberId = #{memberId}
			""")
	public List<Article> getLikeArticles(int memberId);
	
	/*
	 * 현재 가장 높은 게시물 ID에 1을 더한 값을 조회하는 MyBatis 매퍼
	 * 게시물 작성시 다음 게시물의 번호를 정해 놓기 위해서
	 */
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