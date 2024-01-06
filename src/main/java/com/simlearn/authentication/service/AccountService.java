package com.simlearn.authentication.service;

import com.simlearn.authentication.dto.AccountDto;
import com.simlearn.authentication.dto.GameDto;
import com.simlearn.authentication.dto.ResetPasswordDto;
import com.simlearn.authentication.entity.AccountEntity;

import java.util.List;

public interface AccountService {
    void createAccount(AccountDto accountDto);
    void deleteAccount(String username);
    boolean checkUserName(String username);
    boolean checkEmail(String email);
    void resetPassword(ResetPasswordDto resetPasswordDto);
    void forgetPassword(String username, String email);
    void updateNewPassword(ResetPasswordDto resetPasswordDto);
    void updateGamesList(String username, GameDto gameDto);
    List<GameDto> getAllGamesForStudent(String username);
    String saveProfilePicture(String username, byte[] file);
    AccountEntity getProfilePicture(String username);

}
