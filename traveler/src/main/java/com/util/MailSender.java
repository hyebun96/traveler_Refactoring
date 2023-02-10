package com.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;
import java.util.Properties;

public class MailSender {
	private FileManager fileManager;
	private MyUtil myUtil;
	
	private String mailType; // 메일 타입
	private String encType;
	private String pathname;
	private final String pw = "";	// google 앱 비밀번호
	
	public MailSender() {
		this.encType = "utf-8";
		this.mailType = "text/html; charset=utf-8";
		this.pathname = "c:"+File.separator+"temp"+File.separator+"mail";
	}

	public void setMailType(String mailType, String encType) {
		this.mailType = mailType;
		this.encType = encType;
	}
	
	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

	// gmail은 기본적으로 <a href ...> 태그가 있으면 href를 제거한다.
	// SMTP 권한
	private class SMTPAuthenticator extends Authenticator {

	      public PasswordAuthentication getPasswordAuthentication() {  
         // 지메일은 경고메시지 전송 - 보내는사람의 이메일계정을 구글에서 2단계 보안 설정을 하고 앱비밀번호를 이용
			  // gmail : 프로필(자기이름) 클릭 => 구글계정관리 버튼 클릭 => 좌측화면 보안 => 아래부분 보안수준이 낮은 앱 사용  허용으로 변경
         // 네이버 : 메일 아래부분 환경설정 클릭후 POP3등을 허용
	    	  
	          String username =  "hyebun1996@gmail.com"; // 네이버 사용자;
	          // String username =  "지메일아이디"; // gmail 사용자;
	          String password = pw; // 구글 앱 비패스워드;
	          return new PasswordAuthentication(username, password);  
	       }  
	}
	
	public boolean mailSend(Mail dto) {
		boolean b=false;
		
		Properties p = new Properties();   
  
		// SMTP 서버의 계정 설정
		p.put("mail.smtp.user", dto.getSenderEmail());
  
		// SMTP 서버 정보 설정   
		p.put("mail.smtp.host", "smtp.gmail.com"); // 네이버
		       
		// 네이버와 지메일 동일
		p.put("mail.smtp.ssl.protocols", "TLSv1.2");
		p.put("mail.smtp.port", 465);
		p.put("mail.smtp.ssl.enable", "true");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.socketFactory.port", 465);
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.fallback", "false");
		p.put("mail.debug", "true");
		
		try {
			Authenticator auth = new SMTPAuthenticator();  
			Session session = Session.getDefaultInstance(p, auth);
			// 메일 전송시 상세 정보 콘솔에 출력 여부
			session.setDebug(true);
			
			Message msg = new MimeMessage(session);

			// 보내는 사람
			if(dto.getSenderName() == null || dto.getSenderName().equals(""))
				msg.setFrom(new InternetAddress(dto.getSenderEmail()));
			else
				msg.setFrom(new InternetAddress(dto.getSenderEmail(), dto.getSenderName(), encType));
			
			// 받는 사람
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(dto.getReceiverEmail()));
			
			// 제목
			msg.setSubject(dto.getSubject());
			
			if(mailType.indexOf("text/html") == -1) {
				dto.setContent(myUtil.htmlSymbols(dto.getContent()));
			}

			msg.setText(dto.getContent());
			msg.setHeader("Content-Type", mailType);
			msg.setHeader("X-Mailer", dto.getSenderName());
			
			// 메일 보낸 날짜
			msg.setSentDate(new Date());
			
			// 메일 전송
			Transport.send(msg);
			
			b=true;
						
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return b;
	}
}
