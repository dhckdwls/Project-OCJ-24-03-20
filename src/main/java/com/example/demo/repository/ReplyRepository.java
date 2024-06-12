package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.vo.Reply;

@Mapper
public interface ReplyRepository {

	// 특정 타입과 ID에 대한 댓글 목록을 가져옴
	@Select("""
				<script>
				SELECT R.*, M.nickname AS extra__writer
				FROM reply AS R
				INNER JOIN `member` AS M
				ON R.memberId = M.id
				WHERE relTypeCode = #{relTypeCode}
				AND relId = #{relId}
				ORDER BY R.id ASC
				<if test="limitFrom >= 0 ">
					LIMIT #{limitFrom}, #{limitTake}
				</if>
				</script>
			""")
	List<Reply> getForPrintReplies(int loginedMemberId, String relTypeCode, int relId, int limitFrom, int limitTake);

	// 댓글 작성
	@Insert("""
				INSERT INTO reply
				SET regDate = NOW(),
				updateDate = NOW(),
				memberId = #{loginedMemberId},
				relTypeCode = #{relTypeCode},
				relId = #{relId},
				`body` = #{body}
			""")
	void writeReply(int loginedMemberId, String relTypeCode, int relId, String body);

	// 최근 삽입된 댓글의 ID를 가져옴
	@Select("SELECT LAST_INSERT_ID()")
	public int getLastInsertId();

	// 특정 ID에 해당하는 댓글을 가져옴
	@Select("""
				SELECT R.*
				FROM reply AS R
				WHERE R.id = #{id}
			""")
	Reply getReply(int id);

	// 특정 ID에 해당하는 댓글 삭제
	@Delete("""
				DELETE FROM reply
				WHERE id = #{id}
			""")
	void deleteReply(int id);

	// 특정 ID에 해당하는 댓글 수정
	@Update("""
			UPDATE reply
			SET `body` = #{body},
			updateDate = NOW()
			WHERE id = #{id}
				""")
	public void modifyReply(int id, String body);

	// 특정 게시물에 대한 댓글 수 조회
	@Select("""
			SELECT COUNT(*) AS cnt
			FROM reply AS R
			WHERE relId = #{relId}
			""")
	int getRepliesCount(int relId);

	// 특정 회원이 작성한 댓글 목록을 가져옴
	@Select("""
			SELECT * FROM reply
			WHERE memberId = #{memberId}
			""")
	List<Reply> getMyReplies(int memberId);

}