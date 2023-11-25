package com.simlearn.authentication.service;

import com.simlearn.authentication.dto.AccountDto;

public interface AccountService {
    void createAccount(AccountDto accountDto);
    boolean checkUserName(String username);
}
