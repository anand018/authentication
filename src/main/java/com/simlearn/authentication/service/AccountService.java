package com.simlearn.authentication.service;

import com.simlearn.authentication.dto.AccountDto;
import com.simlearn.authentication.dto.ResetPasswordDto;

public interface AccountService {
    void createAccount(AccountDto accountDto);
    boolean checkUserName(String username);
    void resetPassword(ResetPasswordDto resetPasswordDto);
}
