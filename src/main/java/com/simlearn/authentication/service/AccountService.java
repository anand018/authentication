package com.simlearn.authentication.service;

import com.simlearn.authentication.dto.AccountDto;
import com.simlearn.authentication.dto.ResetPasswordDto;

public interface AccountService {
    void createAccount(AccountDto accountDto);
    void deleteAccount(String username);
    boolean checkUserName(String username);
    boolean checkEmail(String email);
    void resetPassword(ResetPasswordDto resetPasswordDto);
    void updateNewPassword(ResetPasswordDto resetPasswordDto);

}
