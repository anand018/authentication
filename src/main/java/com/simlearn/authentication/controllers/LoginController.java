package com.simlearn.authentication.controllers;

import com.simlearn.authentication.dto.LoginRequestDto;
import com.simlearn.authentication.dto.LoginResponseDto;
import com.simlearn.authentication.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto logInRequestDto) {
        return loginService.doLogin(logInRequestDto);
    }

    @PostMapping("/validate/{email}/{otp}")
    public boolean validateOTP(@PathVariable String email, @PathVariable String otp) {
        return loginService.validateOTPByEmail(email, otp);
    }

    @PostMapping("/send-otp/{email}")
    public void sendOTP(@PathVariable String email) {
        loginService.sendOTP(email);
    }
}