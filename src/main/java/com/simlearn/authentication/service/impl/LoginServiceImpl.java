package com.simlearn.authentication.service.impl;

import com.simlearn.authentication.constants.ApplicatiopnConstants;
import com.simlearn.authentication.dto.LoginRequestDto;
import com.simlearn.authentication.dto.LoginResponseDto;
import com.simlearn.authentication.entity.AccountEntity;
import com.simlearn.authentication.exception.AuthenticationFailedException;
import com.simlearn.authentication.repository.AccountAndLoginRepository;
import com.simlearn.authentication.service.LoginService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    private AccountAndLoginRepository repository;
    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public LoginResponseDto doLogin(LoginRequestDto loginRequestDto) {
        AccountEntity accountEntity = repository.findByUsername(loginRequestDto.getUsername());
        if (ObjectUtils.isEmpty(accountEntity)) {
            throw new AuthenticationFailedException("Username or password is incorrect");
        }
        if (accountEntity.getFailedLoginAttempts() >= ApplicatiopnConstants.ATTEMPTS_LIMIT && Base64.getEncoder().encodeToString(loginRequestDto.getPassword().getBytes(StandardCharsets.UTF_8)).equals(accountEntity.getPassword())) {
            throw new AuthenticationFailedException("You have exceeded maximum number of login attempts. Try after 30 minutes");
        }

        if (accountEntity.getFailedLoginAttempts() < ApplicatiopnConstants.ATTEMPTS_LIMIT && Base64.getEncoder().encodeToString(loginRequestDto.getPassword().getBytes(StandardCharsets.UTF_8)).equals(accountEntity.getPassword())) {
            accountEntity.setFailedLoginAttempts(0);
            mongoTemplate.updateFirst(new Query(Criteria.where("id").is(accountEntity.getId())), new Update().set("failedLoginAttempts", 0), AccountEntity.class);
            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setEmail(accountEntity.getEmail());
            loginResponseDto.setUsername(accountEntity.getUsername());
            loginResponseDto.setFirstName(accountEntity.getFirstName());
            return loginResponseDto;
        }
        updateLoginAttempts(accountEntity);
        throw new AuthenticationFailedException("Username or password is incorrect");
    }
    private void updateLoginAttempts(AccountEntity accountEntity) {
        Update update = new Update().set("failedLoginAttempts", accountEntity.getFailedLoginAttempts() + 1);
        mongoTemplate.updateFirst(new Query(Criteria.where("id").is(accountEntity.getId())), update, AccountEntity.class);
    }
}
