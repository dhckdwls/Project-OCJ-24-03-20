package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.repository.MemberRepository;
import com.example.demo.util.Ut;
import com.example.demo.vo.Member;
import com.example.demo.vo.ResultData;

@Service
public class MemberService {
	
	//application.yml 에 있는 값들
	//사이트 주소
	@Value("${custom.siteMainUri}")
	private String siteMainUri;
	
	//사이트 이름
	@Value("${custom.siteName}")
	private String siteName;
	
	
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private MailService mailService;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
		this.mailService = mailService;
	}
	
	
	//회원가입 처리
	public ResultData<Integer> join(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		
		//로그인 아이디로 member DB를 검색해서 찾아온다
		Member existsMember = getMemberByLoginId(loginId);
		
		//existsMember가 null이 아니라면 입력된 로그인아이디가 이미 DB에 존재
		//이는 중복된 로그인 아이디
		if (existsMember != null) {
			return ResultData.from("F-7", Ut.f("이미 사용중인 아이디(%s)입니다", loginId));
		}
		
		//이름과 이메일로 동일한 회원 중복가입 방지
		existsMember = getMemberByNameAndEmail(name, email);
		
		//existsMember 가 null이 아니라면 이미 같은 이름과 이메일로 가입된 회원이 존재
		//중복 가입 최대한 막기 위해서
		if (existsMember != null) {
			return ResultData.from("F-8", Ut.f("이미 사용중인 이름(%s)과 이메일(%s)입니다", name, email));
		}
		
		//위 과정을 모두 통과하면 가입하는데 결격사유 없음
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNum, email);

		int id = memberRepository.getLastInsertId();

		return ResultData.from("S-1", "회원가입이 완료되었습니다.", "id", id);

	}
	
	//이름과 이메일로 member정보 조회
	public Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}
	
	//loginId로 member정보 조회
	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}
	
	//id로 멤버 정보 조회
	public Member getMember(int id) {
		return memberRepository.getMember(id);
	}
	
	//회원정보 수정
	public ResultData modify(int loginedMemberId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		memberRepository.modify(loginedMemberId, loginPw, name, nickname, cellphoneNum, email);
		return ResultData.from("S-1", "회원정보 수정 완료");
	}
	
	// 비밀번호를 제외한 회원정보 수정
	public ResultData modifyWithoutPw(int loginedMemberId, String name, String nickname, String cellphoneNum,
			String email) {
		memberRepository.modifyWithoutPw(loginedMemberId, name, nickname, cellphoneNum, email);
		return ResultData.from("S-1", "회원정보 수정 완료");
	}
	
	//회원 탈퇴
	public void doDelete(int id) {
		memberRepository.delete(id);
		
	}
	//회원에게 임시 비밀번호를 이메일로 발송
	public ResultData notifyTempLoginPwByEmail(Member actor) {
		String title = "[" + siteName + "] 임시 패스워드 발송";
		String tempPassword = Ut.getTempPassword(6);
		String body = "<h1>임시 패스워드 : " + tempPassword + "</h1>";
		body += "<a href=\"" + siteMainUri + "/usr/member/login\" target=\"_blank\">로그인 하러가기</a>";

		ResultData sendResultData = mailService.send(actor.getEmail(), title, body);

		if (sendResultData.isFail()) {
			return sendResultData;
		}

		setTempPassword(actor, tempPassword);

		return ResultData.from("S-1", "계정의 이메일주소로 임시 패스워드가 발송되었습니다.");
	}
	
	//임시 비밀번호 생성
	private void setTempPassword(Member actor, String tempPassword) {
		memberRepository.modify(actor.getId(), Ut.sha256(tempPassword), null, null, null, null);
	}
	

}