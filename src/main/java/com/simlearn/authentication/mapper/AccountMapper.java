package com.simlearn.authentication.mapper;

import com.simlearn.authentication.dto.AccountDto;
import com.simlearn.authentication.entity.AccountEntity;

public class AccountMapper {

    public static AccountEntity convertToAccountEntity(AccountDto accountDto) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setFirstName(accountDto.getFirstName());
        accountEntity.setLastName(accountDto.getLastName());
        accountEntity.setEmail(accountDto.getEmail());
        accountEntity.setUsername(accountDto.getUsername());
        accountEntity.setRole(accountDto.getRole());
        return accountEntity;
    }
}
