package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ReactionPointService;
import com.example.demo.service.ReplyService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Reply;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsrReplyController {

	@Autowired
	private Rq rq;

	@Autowired
	private ReplyService replyService;

	@Autowired
	private ReactionPointService reactionPointService;

	// 댓글 작성 처리 메서드
	@RequestMapping("/usr/reply/doWrite")
	@ResponseBody
	public String doWrite(HttpServletRequest req, String relTypeCode, int relId, String body) {

		Rq rq = (Rq) req.getAttribute("rq");

		// 필수 파라미터 체크
		if (Ut.isNullOrEmpty(relTypeCode)) {
			return Ut.jsHistoryBack("F-1", "relTypeCode을 입력해주세요");
		}
		if (Ut.isEmpty(relId)) {
			return Ut.jsHistoryBack("F-2", "relId을 입력해주세요");
		}
		if (Ut.isNullOrEmpty(body)) {
			return Ut.jsHistoryBack("F-3", "내용을 입력해주세요");
		}

		// 댓글 작성 후 결과 반환
		ResultData<Integer> writeReplyRd = replyService.writeReply(rq.getLoginedMemberId(), relTypeCode, relId, body);

		int id = (int) writeReplyRd.getData1();

		return Ut.jsReplace(writeReplyRd.getResultCode(), writeReplyRd.getMsg(), "../article/detail?id=" + relId);

	}

	// 댓글 삭제 처리 메서드
	@RequestMapping("/usr/reply/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id, String replaceUri) {
		Rq rq = (Rq) req.getAttribute("rq");

		// 댓글 정보 가져오기
		Reply reply = replyService.getReply(id);

		// 댓글이 존재하지 않는 경우
		if (reply == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 댓글은 존재하지 않습니다", id));
		}

		// 로그인한 회원이 댓글 삭제 가능한지 확인
		ResultData loginedMemberCanDeleteRd = replyService.userCanDelete(rq.getLoginedMemberId(), reply);

		// 삭제 가능한 경우 댓글 삭제
		if (loginedMemberCanDeleteRd.isSuccess()) {
			replyService.deleteReply(id);
		}

		// 삭제 후 이동할 URI가 지정되지 않은 경우
		if (Ut.isNullOrEmpty(replaceUri)) {
			switch (reply.getRelTypeCode()) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", reply.getRelId());
				break;
			}
		}

		return Ut.jsReplace(loginedMemberCanDeleteRd.getResultCode(), loginedMemberCanDeleteRd.getMsg(), replaceUri);
	}

	// 댓글 수정 처리 메서드
	@RequestMapping("/usr/reply/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, int id, String body) {
		System.err.println(id);
		System.err.println(body);
		Rq rq = (Rq) req.getAttribute("rq");

		// 댓글 정보 가져오기
		Reply reply = replyService.getReply(id);

		// 댓글이 존재하지 않는 경우
		if (reply == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 댓글은 존재하지 않습니다", id));
		}

		// 로그인한 회원이 댓글 수정 가능한지 확인
		ResultData loginedMemberCanModifyRd = replyService.userCanModify(rq.getLoginedMemberId(), reply);

		// 수정 가능한 경우 댓글 수정
		if (loginedMemberCanModifyRd.isSuccess()) {
			replyService.modifyReply(id, body);
		}

		// 수정된 댓글 내용 반환
		reply = replyService.getReply(id);

		return reply.getBody();
	}

}