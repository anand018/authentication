package com.simlearn.authentication.controllers;

import com.simlearn.authentication.dto.AccountDto;
import com.simlearn.authentication.dto.GameDto;
import com.simlearn.authentication.dto.ResetPasswordDto;
import com.simlearn.authentication.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/account")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@RequestBody AccountDto accountDto) {
        accountService.createAccount(accountDto);
    }

    @DeleteMapping("/account/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable String username) {
        accountService.deleteAccount(username);
    }

    @GetMapping("/username/{username}")
    public boolean checkUsername(@PathVariable String username) {
        return accountService.checkUserName(username);
    }

    @GetMapping("/email/{email}")
    public boolean checkEmail(@PathVariable String email) {
        return accountService.checkEmail(email);
    }

    @PostMapping("/password")
    public void resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        accountService.resetPassword(resetPasswordDto);
    }

    @PostMapping("/password/update/direct")
    public void updateNewPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        accountService.updateNewPassword(resetPasswordDto);
    }

    @PostMapping("/account/games/{username}")
    public void updateGames(@PathVariable String username, @RequestBody GameDto gameDto) {
        accountService.updateGamesList(username, gameDto);
    }
    @GetMapping("/account/games/{username}")
    public List<GameDto> findGamesForStudent(@PathVariable String username) {
        return accountService.getAllGamesForStudent(username);
    }
}