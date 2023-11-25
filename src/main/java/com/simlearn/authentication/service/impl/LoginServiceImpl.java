package com.simlearn.authentication.service.impl;

import com.simlearn.authentication.dto.LoginDto;
import com.simlearn.authentication.entity.AccountEntity;
import com.simlearn.authentication.exception.AuthenticationFailedException;
import com.simlearn.authentication.repository.AccountAndLoginRepository;
import com.simlearn.authentication.service.LoginService;
import jakarta.security.auth.message.AuthException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class LoginServiceImpl implements LoginService {

    private
    @Autowired
    AccountAndLoginRepository repository;
    @Override
    public boolean doLogin(LoginDto loginDto) {
        AccountEntity accountEntity = repository.findByUsername(loginDto.getUsername());
        if(ObjectUtils.isEmpty(accountEntity))
            throw new AuthenticationFailedException("Username or Password is incorrect");
        if(Base64.getEncoder().encodeToString(loginDto.getPassword().getBytes(StandardCharsets.UTF_8)).equals(accountEntity.getPassword())) {
            return true;
        }
        throw new AuthenticationFailedException("Username or Password is incorrect");
    }
}
