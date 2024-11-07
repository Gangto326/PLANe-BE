package com.plane.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.plane.common.exception.custom.EmailException;
import com.plane.common.exception.custom.UserUpdateException;
import com.plane.common.util.HashUtil;
import com.plane.common.util.PasswordGenerator;
import com.plane.user.repository.UserRepository;

@Service
public class UserEmailServiceImpl implements UserEmailService {
	
	private final UserRepository userRepository;
	
	private final JavaMailSender emailSender;
	
	private final PasswordGenerator passwordGenerator;
	private final HashUtil hashUtil;
	
	@Autowired
	public UserEmailServiceImpl(UserRepository userRepository, JavaMailSender emailSender,
								PasswordGenerator passwordGenerator, HashUtil hashUtil) {
		this.userRepository = userRepository;
        this.emailSender = emailSender;
        this.passwordGenerator = passwordGenerator;
        this.hashUtil = hashUtil;
    }
	
	@Async
	@Override
	public void sendNewPassword(String userId, String email) {
		
		String newPassword = passwordGenerator.generatePassword();
		
		SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("gwan1039@gmail.com");
        message.setTo(email);
        message.setSubject("[PLANe] 새로운 비밀번호 발급");
        
        String emailContent = String.format("""
                안녕하세요. PLANe입니다.
                
                요청하신 새로운 비밀번호가 발급되었습니다.
                임시 비밀번호: %s
                
                보안을 위해 로그인 후 반드시 비밀번호를 변경해주세요.
                
                감사합니다.
                PLANe 팀
                """, newPassword);
        
        message.setText(emailContent);
        
        try {
        	
        	emailSender.send(message);
        	userRepository.updateUserPassword(userId, hashUtil.hashPassword(newPassword));
        	
        } catch (Exception e) {
        	
            throw new EmailException("이메일 전송 실패");
        }
        
	}

}
