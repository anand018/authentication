package com.simlearn.authentication.service.impl;

import com.simlearn.authentication.constants.ApplicationConstants;
import com.simlearn.authentication.dto.AccountDto;
import com.simlearn.authentication.dto.GameDto;
import com.simlearn.authentication.dto.ResetPasswordDto;
import com.simlearn.authentication.entity.AccountEntity;
import com.simlearn.authentication.entity.GameEntity;
import com.simlearn.authentication.exception.AccountNotFoundException;
import com.simlearn.authentication.exception.InvalidUsernameException;
import com.simlearn.authentication.exception.UnknownException;
import com.simlearn.authentication.exception.handler.InvalidPasswordException;
import com.simlearn.authentication.helper.LoginServiceImplHelper;
import com.simlearn.authentication.mapper.AccountMapper;
import com.simlearn.authentication.repository.AccountAndLoginRepository;
import com.simlearn.authentication.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountAndLoginRepository accountAndLoginRepository;
    @Autowired
    private LoginServiceImplHelper implHelper;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void createAccount(AccountDto accountDto) {
        AccountEntity accountEntity = AccountMapper.convertToAccountEntity(accountDto);
        accountEntity.setPassword(Base64.getEncoder().encodeToString(accountDto.getPassword().getBytes(StandardCharsets.UTF_8)));
        accountAndLoginRepository.save(accountEntity);
    }

    @Override
    public void deleteAccount(String username) {
        accountAndLoginRepository.deleteByUsername(username);
    }

    @Override
    public boolean checkUserName(String username) {
        if (ObjectUtils.isEmpty(accountAndLoginRepository.findByUsername(username)))
            return true;
        return false;
    }

    @Override
    public boolean checkEmail(String email) {
        if (ObjectUtils.isEmpty(accountAndLoginRepository.findByEmail(email)))
            return true;
        return false;
    }

    @Override
    public void resetPassword(ResetPasswordDto resetPasswordDto) {
        AccountEntity accountEntity = accountAndLoginRepository.findByUsername(resetPasswordDto.getUsername());
        if (ObjectUtils.isEmpty(accountEntity))
            throw new InvalidUsernameException("Username is invalid");
        if (accountEntity.getPassword().equals(Base64.getEncoder().encodeToString(resetPasswordDto.getOldPassword().getBytes(StandardCharsets.UTF_8)))) {
            Update update = new Update().set("password", Base64.getEncoder().encodeToString(resetPasswordDto.getNewPassword().getBytes(StandardCharsets.UTF_8)));
            mongoTemplate.updateFirst(new Query(Criteria.where("id").is(accountEntity.getId())), update, AccountEntity.class);
        } else {
            throw new InvalidPasswordException("Password is invalid");
        }
    }

    @Override
    public void forgetPassword(String username, String email) {
        AccountEntity accountEntity = accountAndLoginRepository.findByUsername(username);
        if (ObjectUtils.isEmpty(accountEntity) || !accountEntity.getEmail().equals(email))
            throw new AccountNotFoundException(ApplicationConstants.ACCOUNT_NOT_FOUND);
        implHelper.sendOtp(email);
    }

    @Override
    public void updateNewPassword(ResetPasswordDto resetPasswordDto) {
        AccountEntity accountEntity = accountAndLoginRepository.findByUsername(resetPasswordDto.getUsername());
        if (ObjectUtils.isEmpty(accountEntity))
            throw new InvalidUsernameException("Username is invalid");
        Update update = new Update().set("password", Base64.getEncoder().encodeToString(resetPasswordDto.getNewPassword().getBytes(StandardCharsets.UTF_8)));
        mongoTemplate.updateFirst(new Query(Criteria.where("id").is(accountEntity.getId())), update, AccountEntity.class);
    }

    @Override
    public void updateGamesList(String username, GameDto gameDto) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setGameId(gameDto.getGameId());
        gameEntity.setGameName(gameDto.getGameName());
        gameEntity.setAttempts(gameDto.getAttempts());
        gameEntity.setCourseCode(gameDto.getCourseCode());
        Query query = new Query(Criteria.where("username").is(username));
        Update update = new Update();
        update.push("gamesList", gameEntity);
        mongoTemplate.updateFirst(query, update, AccountEntity.class);
    }

    @Override
    public List<GameDto> getAllGamesForStudent(String username) {
        List<GameEntity> gameEntities = mongoTemplate.findOne(new Query(Criteria.where("username").is(username)), AccountEntity.class).getGamesList();
        return convertToGameDto(gameEntities);
    }

    @Override
    public String saveProfilePicture(String username, byte[] file) {
        try {
            AccountEntity user = accountAndLoginRepository.findByUsername(username);
            if (ObjectUtils.isEmpty(user))
                throw new AccountNotFoundException(ApplicationConstants.ACCOUNT_NOT_FOUND);
            user.setProfilePicture(file);
            accountAndLoginRepository.save(user);
            return ApplicationConstants.SAVED_SUCCESSFULLY;
        } catch (Exception e) {
            log.error(ApplicationConstants.FAILED_TO_SAVE.concat(e.getMessage()));
            throw new UnknownException(ApplicationConstants.FAILED_TO_SAVE);
        }
    }

    @Override
    public AccountEntity getProfilePicture(String username) {
        AccountEntity user = accountAndLoginRepository.findByUsername(username);
        if (ObjectUtils.isEmpty(user))
            throw new AccountNotFoundException(ApplicationConstants.ACCOUNT_NOT_FOUND);
        return user;
    }

    private List<GameDto> convertToGameDto(List<GameEntity> gameEntities) {
        List<GameDto> gameDtoList = new ArrayList<>();
        gameEntities.forEach(gameEntity -> {
            GameDto gameDto = new GameDto();
            gameDto.setGameName(gameEntity.getGameName());
            gameDto.setGameId(gameEntity.getGameId());
            gameDto.setAttempts(gameEntity.getAttempts());
            gameDto.setCourseCode(gameEntity.getCourseCode());
            gameDtoList.add(gameDto);
        });
        return gameDtoList;
    }
}
