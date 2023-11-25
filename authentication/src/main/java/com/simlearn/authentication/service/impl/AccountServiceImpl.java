package com.simlearn.authentication.service.impl;

import com.simlearn.authentication.dto.AccountDto;
import com.simlearn.authentication.entity.AccountEntity;
import com.simlearn.authentication.mapper.AccountMapper;
import com.simlearn.authentication.repository.AccountAndLoginRepository;
import com.simlearn.authentication.service.AccountService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountAndLoginRepository accountAndLoginRepository;
    @Override
    public void createAccount(AccountDto accountDto) {
        AccountEntity accountEntity = AccountMapper.convertToAccountEntity(accountDto);
        accountEntity.setPassword(Base64.getEncoder().encodeToString(accountDto.getPassword().getBytes(StandardCharsets.UTF_8)));
        accountAndLoginRepository.save(accountEntity);
    }

    @Override
    public boolean checkUserName(String username) {
        if(ObjectUtils.isEmpty(accountAndLoginRepository.findByUsername(username))) {
            return true;
        }
        return false;
    }
}
