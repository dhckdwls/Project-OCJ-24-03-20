package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ArticleService;
import com.example.demo.service.MemberService;
import com.example.demo.service.ReplyService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.Member;
import com.example.demo.vo.Reply;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsrMemberController {

	// rq 객체와 연결
	@Autowired
	private Rq rq;

	// memberService와 연결
	@Autowired
	private MemberService memberService;

	// articleService와 연결
	@Autowired
	private ArticleService articleService;

	// replyService와 연결
	@Autowired
	private ReplyService replyService;

	// 로그 아웃 기능
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogout(HttpServletRequest req, @RequestParam(defaultValue = "/") String afterLogoutUri) {
		Rq rq = (Rq) req.getAttribute("rq");

		// 이미 로그아웃된 상태라면 메세지 표출 후 이전페이지로 이동
		if (!rq.isLogined()) {
			return Ut.jsHistoryBack("F-A", "이미 로그아웃 상태입니다");
		}

		// 아니라면 로그아웃
		rq.logout();

		// 로그아웃 후 지정된 uri로 이동
		return Ut.jsReplace("S-1", "로그아웃 되었습니다", afterLogoutUri);
	}

	// 로그인 페이지로 이동 , 이미 로그인 상태시 로그인 불가 뒤로 가기
	@RequestMapping("/usr/member/login")
	public String showLogin(HttpServletRequest req) {

		Rq rq = (Rq) req.getAttribute("rq");
		// 이미 로그인된 상태라면 알람 메세지 후 이전페이지로 이동
		if (rq.isLogined()) {
			return Ut.jsHistoryBack("F-A", "이미 로그인 함");
		}
		// 그게 아니라면 로그인 페이지로 이동
		return "usr/member/login";
	}

	// 실제로 로그인을 해주는 메서드
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(HttpServletRequest req, String loginId, String loginPw,
			@RequestParam(defaultValue = "/") String afterLoginUri) {

		Rq rq = (Rq) req.getAttribute("rq");

		// 이미 로그인한 상태라면 메세지 후 이전페이지로 이동
		if (rq.isLogined()) {
			return Ut.jsHistoryBack("F-A", "이미 로그인 함");
		}

		// 아이디나 비밀번호가 입력되지 않았다면 다시 로그인 페이지로 이동
		// 아이디와 비밀번호는 반드시 기재되어야 하는 사항
		if (Ut.isNullOrEmpty(loginId)) {
			return Ut.jsHistoryBack("F-1", "아이디를 입력해주세요");
		}
		if (Ut.isNullOrEmpty(loginPw)) {
			return Ut.jsHistoryBack("F-2", "비밀번호를 입력해주세요");
		}

		// 입력한 아이디로 회원을 조회하여 가져옴
		Member member = memberService.getMemberByLoginId(loginId);

		// 만약 해당하는 회원이 없다면 실패 메시지를 포함한 결과로 이전 페이지로 돌아감(로그인페이지)
		if (member == null) {
			return Ut.jsHistoryBack("F-3", Ut.f("%s(은)는 존재하지 않는 아이디입니다", loginId));
		}
		// 입력한 비밀번호가 회원의 비밀번호와 일치하지 않으면 실패 메시지를 포함한 결과로 이전 페이지로 돌아감
		if (member.getLoginPw().equals(loginPw) == false) {
			return Ut.jsHistoryBack("F-4", Ut.f("비밀번호가 일치하지 않습니다"));
		}

		// 로그인을 수행
		rq.login(member);
		// 로그인 후 이동할 페이지가 지정되어 있다면 해당 페이지로 리다이렉트하며 로그인 성공 메시지를 포함한 결과를 반환
		if (afterLoginUri.length() > 0) {
			return Ut.jsReplace("S-1", Ut.f("%s님 환영합니다", member.getNickname()), afterLoginUri);
		}
		// 그게 아니라면 기본 리스트 페이지로 이동
		return Ut.jsReplace("S-1", Ut.f("%s님 환영합니다", member.getNickname()), "/");
	}

	// 회원가입 페이지로 이동
	@RequestMapping("/usr/member/join")
	public String showJoin(HttpServletRequest req) {

		return "usr/member/join";
	}

	// 회원가입시 로그인 아이디 중복체크
	@RequestMapping("/usr/member/getLoginIdDup")
	@ResponseBody
	public ResultData getLoginIdDup(String loginId) {
		// 아이디를 입력했는지 확인 공백은 x
		if (Ut.isEmpty(loginId)) {
			return ResultData.from("F-1", "아이디를 입력해주세요");
		}
		// 입력한 아이디로 이미 존재하는 회원을 조회하여 가져옴
		Member existsMember = memberService.getMemberByLoginId(loginId);
		// 이미 존재하는 회원이 있다면 실패 메시지를 반환하고 해당 아이디를 함께 전달
		if (existsMember != null) {
			return ResultData.from("F-2", "해당 아이디는 이미 사용중이야", "loginId", loginId);
		}
		// 아이디가 사용 가능하다면 성공 메시지를 반환하고 해당 아이디를 함께 전달
		return ResultData.from("S-1", "사용 가능!", "loginId", loginId);
	}

	// 실제로 회원가입을 해주는 메서드
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(HttpServletRequest req, String loginId, String loginPw, String name, String nickname,
			String cellphoneNum, String email) {
		Rq rq = (Rq) req.getAttribute("rq");
		// 이미 로그인 상태라면 실패 메시지를 반환
		if (rq.isLogined()) {
			return Ut.jsHistoryBack("F-A", "이미 로그인 상태입니다");
		}

		// 필수 입력 값들이 비어있는지 확인하고, 비어있다면 실패 메시지를 반환
		if (Ut.isNullOrEmpty(loginId)) {
			return Ut.jsHistoryBack("F-1", "아이디를 입력해주세요");
		}
		if (Ut.isNullOrEmpty(loginPw)) {
			return Ut.jsHistoryBack("F-2", "비밀번호를 입력해주세요");
		}
		if (Ut.isNullOrEmpty(name)) {
			return Ut.jsHistoryBack("F-3", "이름을 입력해주세요");
		}
		if (Ut.isNullOrEmpty(nickname)) {
			return Ut.jsHistoryBack("F-4", "닉네임을 입력해주세요");
		}
		if (Ut.isNullOrEmpty(cellphoneNum)) {
			return Ut.jsHistoryBack("F-5", "전화번호를 입력해주세요");

		}
		if (Ut.isNullOrEmpty(email)) {
			return Ut.jsHistoryBack("F-6", "이메일을 입력해주세요");
		}
		// 회원가입을 진행하고 결과를 받음
		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);
		// 회원가입이 실패했다면 실패 메시지를 반환
		if (joinRd.isFail()) {
			return Ut.jsHistoryBack(joinRd.getResultCode(), joinRd.getMsg());
		}
		// 회원가입이 성공하면 회원 정보를 가져온 후 로그인 페이지로 이동
		Member member = memberService.getMember(joinRd.getData1());

		return Ut.jsReplace(joinRd.getResultCode(), joinRd.getMsg(), "../member/login");
	}

	// 마이페이지로 이동
	@RequestMapping("/usr/member/myPage")
	public String showMyPage(HttpServletRequest req, Model model) {
		Rq rq = (Rq) req.getAttribute("rq");

		if (!rq.isLogined()) {
			return Ut.jsHistoryBack("F-A", "로그인후 이동 가능");
		}
		// 로그인된 회원의 id를 가져옴
		int memberId = rq.getLoginedMemberId();

		// 현재 회원이 작성한 글들을 가져옴
		List<Article> articles = articleService.getMyArticles(memberId);

		// 현재 회원이 작성한 댓글들을 가져옴
		List<Reply> replies = replyService.getMyReplies(memberId);

		// 현재 회원이 좋아요한 글들을 가져옴
		List<Article> likeArticles = articleService.getLikeArticles(memberId);

		// 전체 글의 태그를 가져옴
		List<String> footTags = articleService.getArticlesTags();
		model.addAttribute("footTags", footTags);

		// 모델에 데이터 추가
		model.addAttribute("articles", articles);// 회원이 작성한 글
		model.addAttribute("replies", replies);// 회원이 작성한 댓글
		model.addAttribute("likeArticles", likeArticles);// 회원이 좋아요 표시한 글

		// mypage로 이동
		return "usr/member/myPage";
	}

	// 회원정보 수정
	@RequestMapping("/usr/member/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		Rq rq = (Rq) req.getAttribute("rq");

		// 비밀번호 안바꿀 수도 있어서 비번 null 체크는 제거

		// 이름, 닉네임, 전화번호, 이메일이 비어있는지 체크
		if (Ut.isNullOrEmpty(name)) {
			return Ut.jsHistoryBack("F-3", "이름을 입력해주세요");
		}
		if (Ut.isNullOrEmpty(nickname)) {
			return Ut.jsHistoryBack("F-4", "닉네임을 입력해주세요");
		}
		if (Ut.isNullOrEmpty(cellphoneNum)) {
			return Ut.jsHistoryBack("F-5", "전화번호를 입력해주세요");

		}
		if (Ut.isNullOrEmpty(email)) {
			return Ut.jsHistoryBack("F-6", "이메일을 입력해주세요");
		}

		ResultData modifyRd;

		// 비밀번호가 비어있으면 비밀번호를 변경하지 않고 회원 정보를 수정
		if (Ut.isNullOrEmpty(loginPw)) {
			modifyRd = memberService.modifyWithoutPw(rq.getLoginedMemberId(), name, nickname, cellphoneNum, email);
		} else {
			// 비밀번호가 입력되어 있다면 비밀번호와 함께 회원 정보를 수정
			modifyRd = memberService.modify(rq.getLoginedMemberId(), loginPw, name, nickname, cellphoneNum, email);
		}
		// 결과에 따라 적절한 메시지와 함께 마이페이지로 리다이렉트
		return Ut.jsReplace(modifyRd.getResultCode(), modifyRd.getMsg(), "../member/myPage");
	}

	// 비밀번호 확인 페이지로 이동
	@RequestMapping("/usr/member/checkPw")
	public String showCheckPw() {

		return "usr/member/checkPw";
	}

	// 실제로 비밀번호를 확인하는 메서드
	@RequestMapping("/usr/member/doCheckPw")
	public String doCheckPw(String loginPw) {
		
		 // 비밀번호가 비어있는지 확인
		if (Ut.isNullOrEmpty(loginPw)) {
			return rq.historyBackOnView("비번 입력해");
		}
		
		// 입력된 비밀번호와 로그인된 회원의 비밀번호가 일치하는지 확인
		if (rq.getLoginedMember().getLoginPw().equals(loginPw) == false) {
			return rq.historyBackOnView("비번 틀림");
		}
		
		  // 비밀번호가 일치하면 회원 정보 수정 페이지로 이동
		return "usr/member/modify";
	}

	// 회원탈퇴 시켜주는 메서드
	@RequestMapping("/usr/member/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		
		//회원정보 삭제
		memberService.doDelete(id);

		return Ut.jsReplace("S-1", "회원탈퇴되었습니다.", "/");
	}

	// 로그인 아이디를 분실시 아이디 찾기 페이지로 이동
	@RequestMapping("/usr/member/findLoginId")
	public String showFindLoginId() {

		return "usr/member/findLoginId";
	}

	// 실제로 아이디를 찾아주는 메서드
	@RequestMapping("/usr/member/doFindLoginId")
	@ResponseBody
	public String doFindLoginId(@RequestParam(defaultValue = "/") String afterFindLoginIdUri, String name,
			String email) {
		
		 // 이름과 이메일로 회원을 찾음
		Member member = memberService.getMemberByNameAndEmail(name, email);
		

	    // 회원이 없으면 실패 메시지 반환
		if (member == null) {
			return Ut.jsHistoryBack("F-1", "너는 없는 사람이야");
		}
		
		// 회원이 있으면 성공 메시지와 아이디를 반환
		return Ut.jsReplace("S-1", Ut.f("너의 아이디는 [ %s ] 야", member.getLoginId()), afterFindLoginIdUri);
	}

	// 로그인 비밀번호 분실시 비밀번호찾기 페이지로 이동
	@RequestMapping("/usr/member/findLoginPw")
	public String showFindLoginPw() {

		return "usr/member/findLoginPw";
	}

	// 실제로 비밀번호를 찾아주는 메서드
	@RequestMapping("/usr/member/doFindLoginPw")
	@ResponseBody
	public String doFindLoginPw(@RequestParam(defaultValue = "/") String afterFindLoginPwUri, String loginId,
			String email) {
		
	    // 아이디로 회원을 찾음
		Member member = memberService.getMemberByLoginId(loginId);
		
		// 회원이 없으면 실패 메시지 반환
		if (member == null) {
			return Ut.jsHistoryBack("F-1", "너는 없는 사람이야");
		}
		
		// 회원의 이메일과 입력한 이메일이 일치하지 않으면 실패 메시지 반환
		if (member.getEmail().equals(email) == false) {
			return Ut.jsHistoryBack("F-2", "일치하는 이메일이 없는데?");
		}
		
		  // 임시 비밀번호를 회원의 이메일로 발송하고 결과 메시지 반환
		ResultData notifyTempLoginPwByEmailRd = memberService.notifyTempLoginPwByEmail(member);

		return Ut.jsReplace(notifyTempLoginPwByEmailRd.getResultCode(), notifyTempLoginPwByEmailRd.getMsg(),
				afterFindLoginPwUri);
	}

}