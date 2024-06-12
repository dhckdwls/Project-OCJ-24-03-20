package com.example.demo.service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.demo.vo.ResultData;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {
	@Autowired
	private JavaMailSender sender;
	// application.yml 확인
	@Value("${custom.emailFrom}")
	private String emailFrom;
	@Value("${custom.emailFromName}")
	private String emailFromName;

	// 메일을 처리하는 내부 클래스(클래스 안에 클래스)
	// javaMailSender 활용
	private static class MailHandler {
		private JavaMailSender sender;
		private MimeMessage message;
		private MimeMessageHelper messageHelper;

		// MessagingException 메일 처리중 발생한 예외
		public MailHandler(JavaMailSender sender) throws MessagingException {
			this.sender = sender;
			this.message = this.sender.createMimeMessage();
			this.messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		}

		// 보내는 사람의 이메일 주소 설정
		// mail 보내는 사람 이메일 주소
		// name 보내는 사람 이름
		// UnsupportedEncodingException 이메일 주소 인코딩이 지원되지 않는 경우 발생하는 예외
		// MessagingException 메일 처리 중 발생한 예외
		public void setFrom(String mail, String name) throws UnsupportedEncodingException, MessagingException {
			messageHelper.setFrom(mail, name);
		}

		// 수신자 이메일 주소 설정
		// mail 수신자 이메일 주소
		// MessagingException 메일 처리중 발생한 예외
		public void setTo(String mail) throws MessagingException {
			messageHelper.setTo(mail);
		}

		// 메일 제목 설정
		public void setSubject(String subject) throws MessagingException {
			messageHelper.setSubject(subject);
		}

		// 메일 본문 설정
		// text 메일 본문
		public void setText(String text) throws MessagingException {
			messageHelper.setText(text, true);
		}

		// 메일 발송
		public void send() {
			try {
				sender.send(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//메일 발송
	// email 수신자 이메일 주소
	// title 메일 제목
	// body 메일 본문
	public ResultData send(String email, String title, String body) {

		MailHandler mail;
		try {
			mail = new MailHandler(sender);
			mail.setFrom(emailFrom.replaceAll(" ", ""), emailFromName);
			mail.setTo(email);
			mail.setSubject(title);
			mail.setText(body);
			mail.send();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultData.from("F-1", "메일 발송 실패하였습니다.");
		}

		return ResultData.from("S-1", "메일이 발송되었습니다.");
	}
}
