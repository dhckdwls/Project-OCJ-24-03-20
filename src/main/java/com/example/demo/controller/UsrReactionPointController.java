package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ArticleService;
import com.example.demo.service.ReactionPointService;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;

@Controller
public class UsrReactionPointController {
	
	//request 관련 연결
	@Autowired
	private Rq rq;
	
	//articleService 연결
	@Autowired
	private ArticleService articleService;
	
	//reactionPointService 연결
	@Autowired
	private ReactionPointService reactionPointService;
	
	//좋아요 리액션 처리 메서드
	@RequestMapping("/usr/reactionPoint/doGoodReaction")
	@ResponseBody
	public ResultData doGoodReaction(String relTypeCode, int relId, String replaceUri) {
		
		  // 유저의 해당 게시물 리액션 상태 확인
		ResultData usersReactionRd = reactionPointService.usersReaction(rq.getLoginedMemberId(), relTypeCode, relId);

		int usersReaction = (int) usersReactionRd.getData1();
		
		 // 유저가 이미 좋아요를 누른 경우
		if (usersReaction == 1) {
			// 좋아요 취소 처리
			ResultData rd = reactionPointService.deleteGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			int goodRP = articleService.getGoodRP(relId);
			return ResultData.from("S-1", "좋아요 취소", "goodRP", goodRP);
		} 
		   // 유저가 싫어요를 이미 누른 경우
		else if (usersReaction == -1) {
		    // 싫어요를 취소하고 좋아요를 누름
			ResultData rd = reactionPointService.deleteBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			rd = reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			int goodRP = articleService.getGoodRP(relId);

			return ResultData.from("S-2", "싫어요 눌렀잖어", "goodRP", goodRP);
		}
		
		// 좋아요를 누른 경우
		ResultData reactionRd = reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		
		   // 실패시 메시지 반환
		if (reactionRd.isFail()) {
			return ResultData.from(reactionRd.getResultCode(), reactionRd.getMsg());
		}

		int goodRP = articleService.getGoodRP(relId);

		return ResultData.from(reactionRd.getResultCode(), reactionRd.getMsg(), "goodRP", goodRP);
	}
	
	// 싫어요 리액션 처리 메서드
	@RequestMapping("/usr/reactionPoint/doBadReaction")
	@ResponseBody
	public ResultData doBadReaction(String relTypeCode, int relId, String replaceUri) {
		
        // 유저의 해당 게시물 리액션 상태 확인
		ResultData usersReactionRd = reactionPointService.usersReaction(rq.getLoginedMemberId(), relTypeCode, relId);

		int usersReaction = (int) usersReactionRd.getData1();
		
	    // 유저가 이미 싫어요를 누른 경우
		if (usersReaction == -1) {
		    // 싫어요 취소 처리
			ResultData rd = reactionPointService.deleteBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			int goodRP = articleService.getGoodRP(relId);

			return ResultData.from("S-1", "싫어요 취소", "goodRP", goodRP);
		} 
		   // 유저가 좋아요를 이미 누른 경우
		else if (usersReaction == 1) {
			  // 좋아요를 취소하고 싫어요를 누름
			ResultData rd = reactionPointService.deleteGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			rd = reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			int goodRP = articleService.getGoodRP(relId);

			return ResultData.from("S-2", "좋아요 눌렀잖어", "goodRP", goodRP);
		}
		
		// 싫어요를 누른 경우
		ResultData reactionRd = reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		
		// 실패시 메시지 반환
		if (reactionRd.isFail()) {
			return ResultData.from(reactionRd.getResultCode(), reactionRd.getMsg());
		}

		int goodRP = articleService.getGoodRP(relId);

		return ResultData.from(reactionRd.getResultCode(), reactionRd.getMsg(), "goodRP", goodRP);
	}

}