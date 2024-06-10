package com.example.demo.vo;

import java.io.IOException;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.example.demo.service.MemberService;
import com.example.demo.util.Ut;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;

//Rq 는 세션에 접근하는 친구라고 생각하면 된다 메번 메서드에서 세션을 연결해야 하고 request 객체를 만들어야 하는 상황때문에
// 코드의 재활용을 하기 위해서 따로 만들어준 클래스
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {
	@Getter
	private boolean isLogined; // 로그인 여부
	@Getter
	private int loginedMemberId; // 로그인된 회원 ID
	@Getter
	private Member loginedMember; // 로그인된 회원 정보

	private HttpSession session; // 세션 객체
	private HttpServletRequest req; // HTTP 요청 객체
	private HttpServletResponse resp; // HTTP 응답 객체

	// 생성자: 요청, 응답 객체와 회원 서비스를 받아 초기화
	public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {
		this.req = req;
		this.resp = resp;
		this.session = req.getSession();

		HttpSession httpSession = req.getSession();

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true; // 로그인 상태로 설정
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId"); // 로그인된 회원 ID 설정
			loginedMember = memberService.getMember(loginedMemberId); // 로그인된 회원 정보 설정
		}

		this.req.setAttribute("rq", this); // 요청 속성에 Rq 객체 설정
	}

	// 경고 메시지를 출력하고 이전 페이지로 돌아가는 스크립트 출력
	public void printHistoryBack(String msg) throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		println("<script>");
		if (!Ut.isEmpty(msg)) {
			System.err.println("alert('" + msg + "');");
			println("alert('" + msg + "');"); // 경고 메시지 출력
		}
		println("history.back();"); // 이전 페이지로 돌아감
		println("</script>");
	}

	// 문자열을 HTML로 출력
	private void println(String str) {
		print(str + "\n");
	}

	// 문자열을 출력
	private void print(String str) {
		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 로그아웃 처리
	public void logout() {
		session.removeAttribute("loginedMemberId"); // 세션에서 로그인된 회원 ID 제거
		session.removeAttribute("loginedMember"); // 세션에서 로그인된 회원 정보 제거
	}

	// 로그인 처리
	public void login(Member member) {
		session.setAttribute("loginedMemberId", member.getId()); // 세션에 로그인된 회원 ID 설정
		session.setAttribute("loginedMember", member); // 세션에 로그인된 회원 정보 설정
	}

	// 액션 인터셉터 초기화 (현재는 구현 내용 없음)
	public void initBeforeActionInterceptor() {
	}

	// 경고 메시지와 함께 이전 페이지로 돌아가는 뷰 반환
	public String historyBackOnView(String msg) {
		req.setAttribute("msg", msg); // 요청 속성에 메시지 설정
		req.setAttribute("historyBack", true); // 요청 속성에 historyBack 설정
		return "usr/common/js"; // 공통 JS 뷰 반환
	}

	// 현재 URI를 반환
	public String getCurrentUri() {
		String currentUri = req.getRequestURI(); // 요청 URI 가져오기
		String queryString = req.getQueryString(); // 쿼리 문자열 가져오기

		System.err.println(currentUri);
		System.err.println(queryString);

		if (currentUri != null && queryString != null) {
			currentUri += "?" + queryString; // 쿼리 문자열이 있으면 현재 URI에 추가
		}

		System.out.println(currentUri);

		return currentUri;
	}

	// JavaScript를 이용하여 페이지를 교체하는 스크립트 출력
	public void jsprintReplace(String resultCode, String msg, String replaceUri) {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsReplace(resultCode, msg, replaceUri)); // JavaScript 교체 함수 출력
	}

	// 로그인 페이지 URI 반환
	public String getLoginUri() {
		return "../member/login?afterLoginUri=" + getAfterLoginUri(); // 로그인 후 돌아갈 URI 포함
	}

	// 로그인 후 돌아갈 URI 반환
	private String getAfterLoginUri() {
		return getEncodedCurrentUri();
	}

	// 현재 URI를 인코딩하여 반환
	public String getEncodedCurrentUri() {
		return Ut.getEncodedCurrentUri(getCurrentUri());
	}

	// 로그아웃 URI 반환
	public String getLogoutUri() {
		return "../member/doLogout?afterLogoutUri=" + getAfterLogoutUri(); // 로그아웃 후 돌아갈 URI 포함
	}

	// 로그아웃 후 돌아갈 URI 반환
	private String getAfterLogoutUri() {
		return getEncodedCurrentUri();
	}

	// 이미지 URI 반환
	public String getImgUri(int id) {
		return "/common/genFile/file/article/" + id + "/extra/Img/1";
	}

	// 프로필 기본 이미지 URI 반환
	public String getProfileFallbackImgUri() {
		return "https://via.placeholder.com/150/?text=*^_^*";
	}

	// 프로필 이미지 로드 실패 시 기본 이미지로 대체하는 HTML 반환
	public String getProfileFallbackImgOnErrorHtml() {
		return "this.src = '" + getProfileFallbackImgUri() + "'";
	}

	// 로그인 ID 찾기 페이지 URI 반환
	public String getFindLoginIdUri() {
		return "../member/findLoginId?afterFindLoginIdUri=" + getAfterFindLoginIdUri(); // ID 찾기 후 돌아갈 URI 포함
	}

	// 로그인 ID 찾기 후 돌아갈 URI 반환
	private String getAfterFindLoginIdUri() {
		return getEncodedCurrentUri();
	}

	// 로그인 비밀번호 찾기 페이지 URI 반환
	public String getFindLoginPwUri() {
		return "../member/findLoginPw?afterFindLoginPwUri=" + getAfterFindLoginPwUri(); // 비밀번호 찾기 후 돌아갈 URI 포함
	}

	// 로그인 비밀번호 찾기 후 돌아갈 URI 반환
	private String getAfterFindLoginPwUri() {
		return getEncodedCurrentUri();
	}
}