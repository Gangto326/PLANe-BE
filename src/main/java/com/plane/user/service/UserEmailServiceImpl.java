package com.plane.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.plane.common.exception.custom.EmailException;
import com.plane.common.exception.custom.UserNotFoundException;
import com.plane.common.util.HashUtil;
import com.plane.common.util.PasswordGenerator;
import com.plane.user.dto.EmailVerificationRequest;
import com.plane.user.dto.FindPasswordRequest;
import com.plane.user.repository.UserRepository;

import jakarta.mail.internet.MimeMessage;

@Service
public class UserEmailServiceImpl implements UserEmailService {
	
	private static final String SENDER_EMAIL = "gwan1039@gmail.com";
	private final JavaMailSender javaMailSender;
	private final UserRepository userRepository;
	private final PasswordGenerator passwordGenerator;
	private final HashUtil hashUtil;
	
	@Autowired
	public UserEmailServiceImpl(UserRepository userRepository, JavaMailSender javaMailSender,
								PasswordGenerator passwordGenerator, HashUtil hashUtil) {
		this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
        this.passwordGenerator = passwordGenerator;
        this.hashUtil = hashUtil;
    }
	
	
	public MimeMessage createMail(String receiver, String subject, String content) throws Exception {
		
        MimeMessage mail = javaMailSender.createMimeMessage();
        
        mail.setFrom(SENDER_EMAIL);
        mail.setRecipients(MimeMessage.RecipientType.TO, receiver);
        mail.setSubject(subject);
        mail.setText(content, "UTF-8", "html");

        return mail;
    }
	
	
	@Async
	@Override
	public void sendNewPassword(FindPasswordRequest findPasswordRequest) {
		
		if (!userRepository.existsById(findPasswordRequest.getUserId())) {
			throw new UserNotFoundException("해당 ID를 사용하는 회원이 없습니다.");
		}
		
		String newPassword = passwordGenerator.generatePassword();

		String subject = "[PLANe] 새로운 비밀번호 발급";
		
        String content = String.format("""
                <h3>안녕하세요. PLANe입니다.</h3>
                
                요청하신 새로운 비밀번호가 발급되었습니다.
                임시 비밀번호: %s
                
                보안을 위해 로그인 후 반드시 비밀번호를 변경해주세요.
                
                감사합니다.
                PLANe 팀
                """, newPassword);
        
        try {
        	javaMailSender.send(createMail(findPasswordRequest.getEmail(), subject, content));
        	userRepository.updateUserPassword(findPasswordRequest.getUserId(), hashUtil.hashPassword(newPassword));        	
        } catch (Exception e) {
            throw new EmailException("이메일 전송 실패");
        }
        
	}


	@Override
	public void sendVerificationCode(EmailVerificationRequest findIdRequest) {
		
		if (!userRepository.existsByEmail(findIdRequest.getEmail())) {
			throw new UserNotFoundException("해당 이메일로 등록된 ID가 없습니다.");
		}
		
		String verificationCode = passwordGenerator.generatePassword();

		String subject = "[PLANe] 인증번호 발급";
		
        String content = String.format("""
                <h3>안녕하세요. PLANe입니다.</h3>
                
                인증번호가 발급되었습니다.
                인증번호: %s
                
                해당 인증번호를 PLANe 사이트에 입력하여 인증하여 주시기 바랍니다.
                
                감사합니다.
                PLANe 팀
                """, verificationCode);
        
        try {
        	javaMailSender.send(createMail(findIdRequest.getEmail(), subject, content));
        	userRepository.insertVerificationCode(findIdRequest.getEmail(), verificationCode);   	
        } catch (Exception e) {
            throw new EmailException("이메일 전송 실패");
        }
	}

}
