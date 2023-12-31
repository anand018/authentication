package com.simlearn.authentication.service;

import com.simlearn.authentication.dto.LoginRequestDto;
import com.simlearn.authentication.dto.LoginResponseDto;

public interface LoginService {
    LoginResponseDto doLogin(LoginRequestDto loginRequestDto);
    boolean validateOTPByEmail(String email, String otp);
    void sendOTP(String email);
}
