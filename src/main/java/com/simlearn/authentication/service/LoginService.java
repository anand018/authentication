package com.simlearn.authentication.service;

import com.simlearn.authentication.dto.LoginDto;

public interface LoginService {
    boolean doLogin(LoginDto loginDto);
}
