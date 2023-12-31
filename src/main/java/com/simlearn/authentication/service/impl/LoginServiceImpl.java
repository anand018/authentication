package com.simlearn.authentication.service.impl;

import static com.simlearn.authentication.constants.ApplicatiopnConstants.*;

import com.simlearn.authentication.dto.LoginRequestDto;
import com.simlearn.authentication.dto.LoginResponseDto;
import com.simlearn.authentication.entity.AccountEntity;
import com.simlearn.authentication.exception.AuthenticationFailedException;
import com.simlearn.authentication.helper.LoginServiceImplHelper;
import com.simlearn.authentication.repository.AccountAndLoginRepository;
import com.simlearn.authentication.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Base64;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AccountAndLoginRepository repository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private LoginServiceImplHelper implHelper;


    @Override
    public LoginResponseDto doLogin(LoginRequestDto loginRequestDto) {
        AccountEntity accountEntity = repository.findByUsername(loginRequestDto.getUsername());
        validateLoginRequest(accountEntity, loginRequestDto);
        return authenticateUser(accountEntity, loginRequestDto);
    }

    @Override public boolean validateOTPByEmail(String email, String otp) {
        return implHelper.validateOtp(email, otp);
    }

    @Override
    public void sendOTP(String email) {
        implHelper.sendOtp(email);
        log.info("OTP is sent to email: ".concat(email));
    }

    private LoginResponseDto authenticateUser(AccountEntity accountEntity, LoginRequestDto loginRequestDto) {
        if (accountEntity.getFailedLoginAttempts() < ATTEMPTS_LIMIT && Base64.getEncoder().encodeToString(loginRequestDto.getPassword().getBytes(StandardCharsets.UTF_8)).equals(accountEntity.getPassword())) {
            accountEntity.setFailedLoginAttempts(0);
            mongoTemplate.updateFirst(new Query(Criteria.where("id").is(accountEntity.getId())), new Update().set("failedLoginAttempts", ZERO), AccountEntity.class);
            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setEmail(accountEntity.getEmail());
            loginResponseDto.setUsername(accountEntity.getUsername());
            loginResponseDto.setFullName(StringUtils.trim(accountEntity.getFirstName()).concat(" ").concat(accountEntity.getLastName()));
            if (Arrays.asList(accountEntity.getRole()).contains(ADMIN)) {
                implHelper.sendOtp(accountEntity.getEmail());
                loginResponseDto.setValidateByEmailOTP(true);
            }
            log.info(LOGIN_SUCCESS.concat(accountEntity.getUsername()));
            return loginResponseDto;
        }
        updateFailedLoginAttempts(accountEntity);
        log.error(FAILED_TO_LOGIN.concat(accountEntity.getUsername()).concat(FAILURE_REASON).concat(INCORRECT_USERNAME_PASSWORD));
        throw new AuthenticationFailedException(INCORRECT_USERNAME_PASSWORD);
    }

    private void updateFailedLoginAttempts(AccountEntity accountEntity) {
        accountEntity.setLastLoginFailedAt(LocalDateTime.now().toString());
        Update update = new Update();
        update.set("failedLoginAttempts", accountEntity.getFailedLoginAttempts() + 1);
        update.set("lastLoginFailedAt", accountEntity.getLastLoginFailedAt());
        mongoTemplate.updateFirst(new Query(Criteria.where("id").is(accountEntity.getId())), update, AccountEntity.class);
    }

    private void validateLoginRequest(AccountEntity accountEntity, LoginRequestDto loginRequestDto) {
        if (ObjectUtils.isEmpty(accountEntity)) {
            log.error(FAILED_TO_LOGIN.concat(accountEntity.getUsername()).concat(FAILURE_REASON).concat(INCORRECT_USERNAME_PASSWORD));
            throw new AuthenticationFailedException(INCORRECT_USERNAME_PASSWORD);
        }
        if (!Arrays.asList(accountEntity.getRole()).contains(loginRequestDto.getRole())) {
            log.error(FAILED_TO_LOGIN.concat(accountEntity.getUsername()).concat(FAILURE_REASON).concat(NO_ACCESS_TO_LOGIN));
            throw new AuthenticationFailedException(NO_ACCESS_TO_LOGIN);
        }
        if (accountEntity.getFailedLoginAttempts() >= ATTEMPTS_LIMIT && Base64.getEncoder().encodeToString(loginRequestDto.getPassword().getBytes(StandardCharsets.UTF_8)).equals(accountEntity.getPassword()) && ChronoUnit.SECONDS.between(LocalDateTime.parse(accountEntity.getLastLoginFailedAt()), LocalDateTime.now()) >= COOLING_PERIOD)
            accountEntity.setFailedLoginAttempts(0);
        if (accountEntity.getFailedLoginAttempts() >= ATTEMPTS_LIMIT && Base64.getEncoder().encodeToString(loginRequestDto.getPassword().getBytes(StandardCharsets.UTF_8)).equals(accountEntity.getPassword())) {
            throw new AuthenticationFailedException(EXCEEDED_ATTEMPTS);
        }
    }
}
