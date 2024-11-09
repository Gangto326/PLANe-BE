package com.plane.user.service;

import com.plane.user.dto.EmailVerificationRequest;
import com.plane.user.dto.FindPasswordRequest;

import jakarta.validation.Valid;

public interface UserEmailService {

	void sendNewPassword(FindPasswordRequest findPasswordRequest);

	void sendVerificationCode(EmailVerificationRequest findIdRequest);

}
