package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ReplyRepository;
import com.example.demo.util.Ut;
import com.example.demo.vo.Reply;
import com.example.demo.vo.ResultData;

@Service
public class ReplyService {
	
	//시점의 차이 떄문에 발생하는 문제를 해결하기 위해서 replyRepository생성
	//또한 replyService의 생성자를 통해서 값을 채우기
	@Autowired
	private ReplyRepository replyRepository;

	public ReplyService(ReplyRepository replyRepository) {
		this.replyRepository = replyRepository;
	}
	
	//해당 게시물의 댓글 목록을 페이지 작업해서 가져오기
	public List<Reply> getForPrintReplies(int loginedMemberId, String relTypeCode, int relId, int itemsInAPageReply, int page) {
		
		int limitFrom = (page - 1) * itemsInAPageReply;
		int limitTake = itemsInAPageReply;
		
		List<Reply> replies = replyRepository.getForPrintReplies(loginedMemberId, relTypeCode, relId,limitFrom,limitTake);
		
		//수정 삭제 권한 확인하기
		for (Reply reply : replies) {
			controlForPrintData(loginedMemberId, reply);
		}

		return replies;
	}
	
	//댓글을 작성하는 기능
	public ResultData<Integer> writeReply(int loginedMemberId, String relTypeCode, int relId, String body) {
		//작성자 정보와 어떤 typecode에 어떤 id에 어떤 내용을 적을 것인지
		replyRepository.writeReply(loginedMemberId, relTypeCode, relId, body);

		int id = replyRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%d번 댓글이 생성되었습니다", id), "id", id);
	}
	
	//댓글에 대한 수정,삭제 권한 여부 체크
	private void controlForPrintData(int loginedMemberId, Reply reply) {
		if (reply == null) {
			return;
		}
		ResultData userCanModifyRd = userCanModify(loginedMemberId, reply);
		reply.setUserCanModify(userCanModifyRd.isSuccess());

		ResultData userCanDeleteRd = userCanDelete(loginedMemberId, reply);
		reply.setUserCanDelete(userCanDeleteRd.isSuccess());
	}
	
	//댓글에 대한 삭제 권한 여부 체크
	public ResultData userCanDelete(int loginedMemberId, Reply reply) {

		if (reply.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("%d번 댓글에 대한 삭제 권한이 없습니다", reply.getId()));
		}

		return ResultData.from("S-1", Ut.f("%d번 댓글이 삭제 되었습니다", reply.getId()));
	}
	
	//댓글에 대한 수정 권한 여부 체크
	public ResultData userCanModify(int loginedMemberId, Reply reply) {

		if (reply.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("%d번 댓글에 대한 수정 권한이 없습니다", reply.getId()));
		}

		return ResultData.from("S-1", Ut.f("%d번 댓글을 수정했습니다", reply.getId()));
	}
	
	//댓글 가져오기
	public Reply getReply(int id) {
		return replyRepository.getReply(id);
	}
	
	//댓글 삭제
	public ResultData deleteReply(int id) {
		replyRepository.deleteReply(id);
		return ResultData.from("S-1", Ut.f("%d번 댓글을 삭제했습니다", id));
	}
	//댓글 수정
	public void modifyReply(int id, String body) {
		replyRepository.modifyReply(id, body);
	}
	//게시물에 작성되어있는 댓글의 총 개수
	public int getRepliesCount(int relId) {
		return replyRepository.getRepliesCount(relId);
	}
	
	//특정 회원이 작성한 댓글들 가져오기
	public List<Reply> getMyReplies(int memberId) {
		List<Reply> replies = replyRepository.getMyReplies(memberId);
		return replies;
	}
}